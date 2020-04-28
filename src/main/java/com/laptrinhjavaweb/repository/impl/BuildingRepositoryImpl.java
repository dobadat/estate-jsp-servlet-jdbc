package com.laptrinhjavaweb.repository.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.laptrinhjavaweb.annotation.Column;
import com.laptrinhjavaweb.builder.BuilderSearchBuilder;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.entity.RentAreaEntity;
import com.laptrinhjavaweb.entity.UserEntity;
import com.laptrinhjavaweb.mapper.ResultSetMapper;
import com.laptrinhjavaweb.paging.Pageable;
import com.laptrinhjavaweb.repository.BuildingRepository;

public class BuildingRepositoryImpl extends SimpleJpaRepository<BuildingEntity> implements BuildingRepository {

	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params, Pageable pageable,
			BuilderSearchBuilder fieldSearch) {
		StringBuilder sqlSearch = new StringBuilder("Select * from building A ");
		if (StringUtils.isNotBlank(fieldSearch.getStaffId())) {
			sqlSearch.append(" INNER JOIN assignmentstaff assignmentstaff ON A.id = assignmentstaff.buildingid ");
		}
		sqlSearch.append("WHERE 1=1");
		sqlSearch = this.createSQLfindAll(sqlSearch, params);
		String sqlSpecial = buildingSqlSpecial(fieldSearch);
		sqlSearch.append(sqlSpecial);
		return this.findAll(sqlSearch.toString(), pageable);
	}

	// dung de check >=< trong BuilderSearchBuilder
	private String buildingSqlSpecial(BuilderSearchBuilder fieldSearch) {
		StringBuilder result = new StringBuilder("");
		if (StringUtils.isNotBlank(fieldSearch.getCostRentFrom())) {
			result.append(" AND cosrent >= " + fieldSearch.getCostRentFrom() + "");
		}
		if (StringUtils.isNotBlank(fieldSearch.getCostRentTo())) {
			result.append(" AND cosrent <= " + fieldSearch.getCostRentTo() + "");
		}
		if (fieldSearch.getBuildingTypes().length > 0) {
			result.append(" AND (");
			result.append(" A.type like'%" + fieldSearch.getBuildingTypes()[0] + "%'");
			Arrays.stream(fieldSearch.getBuildingTypes())
					.filter(item -> !item.endsWith(fieldSearch.getBuildingTypes()[0]))
					.forEach(item -> result.append(" OR A.type like'%" + item + "%' "));

			result.append(" ) ");
		}
		if (StringUtils.isNotBlank(fieldSearch.getAreaRentFrom())
				|| StringUtils.isNotBlank(fieldSearch.getAreaRentTo())) {
			result.append(" AND EXISTS (SELECT * From rentarea ra WHERE (ra.buildingid = A.id");
			if (fieldSearch.getAreaRentFrom() != null) {
				result.append(" AND ra.value >= " + fieldSearch.getAreaRentFrom() + "");

			}
			if (fieldSearch.getAreaRentTo() != null) {
				result.append(" AND ra.value >= " + fieldSearch.getAreaRentTo() + "");
			}
			result.append("))");
		}
		if (StringUtils.isNotBlank(fieldSearch.getStaffId())) {
			result.append(" AND assignmentstaff.staffid =  " + fieldSearch.getStaffId());
		}

		return result.toString();
	}

	@Override
	public List<BuildingEntity> save(BuildingEntity buildingEntity) {
		Long id = this.insert(buildingEntity);
		return this.findBy(id);
	}

	@Override
	public BuildingEntity deleteID(BuildingEntity buildingEntity) {
		delete(buildingEntity.getId());
		return null;
	}

	@Override
	public BuildingEntity updateID(BuildingEntity buildingEntity) {
		Long id = this.update(buildingEntity);
		return null;
	}

	@Override
	public BuildingEntity deleteString(BuilderSearchBuilder fieldSearch) {
		// delete by name have error, why ?
		StringBuilder sql = new StringBuilder("delete from ");
		if (StringUtils.isNotBlank(fieldSearch.getStaffId())) {
			sql.append(" assignmentstaff WHERE ");
			sql.append(
					" EXISTS (SELECT * FROM building WHERE building.id = assignmentstaff.buildingid AND assignmentstaff.staffid = "
							+ fieldSearch.getStaffId() + ") ");
		} else {
			sql.append(" building Where 1=1 ");
			Field[] fields = BuilderSearchBuilder.class.getDeclaredFields();
			for (Field field : fields) {
				try {
					if (!field.getName().equals("buildingTypes")) {
						field.setAccessible(true);
						if (field.get(fieldSearch) instanceof String) {
							if (field.getName().equals("buildingArea") || field.getName().equals("numberOfBasement")) {
								if (field.get(fieldSearch) != null
										&& StringUtils.isNotEmpty((String) field.get(fieldSearch))) {
									sql.append(" AND " + field.getName().toLowerCase() + " = "
											+ Integer.parseInt((String) field.get(fieldSearch)));
								}
							} else {
								sql.append(" AND " + field.getName().toLowerCase() + " = '" + field.get(fieldSearch)
										+ "'");
							}
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}

			}
		}
		this.delete(sql.toString());
		return null;
	}

	@Override
	public List<BuildingEntity> saveNew(BuildingEntity buildingEntity, BuilderSearchBuilder fieldSearch,
			RentAreaEntity areaEntity) {
		StringBuilder sb = new StringBuilder();
		if (fieldSearch.getBuildingTypes() != null ) {
			String[] types = fieldSearch.getBuildingTypes();
			for (int y = 0; y < types.length; y++) {
				if (y > 0) {
					sb.append(",");
				}
				String item = types[y];
				sb.append(item);
			}
		}
		Long id = this.insert(buildingEntity,sb);
		if (StringUtils.isNotBlank(fieldSearch.getAreaRent())) {
			String sql = "INSERT INTO rentarea(value,buildingid) VALUES(?,?);";
			Connection connection = null;
			PreparedStatement statement = null;
			try {
				connection = EntityManagerImpl.getConnection();
				connection.setAutoCommit(false);
				statement = connection.prepareStatement(sql);
				String src = fieldSearch.getAreaRent();
				String[] words = src.split(",");
				for (String re : words) {
					statement.setObject(1, re);
					statement.setObject(2, id);
					statement.executeUpdate();
				}
				connection.commit();
			} catch (SQLException e) {
				if (connection != null) {
					try {
						connection.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			} finally {
				try {
					if (connection != null) {
						connection.close();
					}
					if (statement != null) {
						statement.close();
					}

				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}

		}
		return null;
	}

	@Override
	public List<BuildingEntity> BuildingDeliverble(BuildingEntity buildingEntity) {

		return null;
	}

}
