package com.laptrinhjavaweb.repository.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.laptrinhjavaweb.builder.CustomerSearchBuilder;
import com.laptrinhjavaweb.entity.CustomerEntity;
import com.laptrinhjavaweb.paging.Pageable;
import com.laptrinhjavaweb.repository.CustomerRepository;

public class CustomerRepositoryImpl extends SimpleJpaRepository<CustomerEntity> implements CustomerRepository {

	@Override
	public List<CustomerEntity> findAllCustomer(Map<String, Object> params, Pageable pageable,
			CustomerSearchBuilder fieldSearch) {
		StringBuilder sqlSearch = new StringBuilder("Select * from customer A ");
		if (StringUtils.isNoneBlank(fieldSearch.getCustomerId())) {
			sqlSearch.append(" inner join user_responsibility U on U.customerid = A.id ");
		}
		sqlSearch.append(" WHERE 1=1 ");
		sqlSearch = this.createSQLfindAll(sqlSearch, params);
		String sqlSpecial = customerSqlSpecial(fieldSearch);
		sqlSearch.append(sqlSpecial);
		return this.findAll(sqlSearch.toString(), pageable);
	}

	private String customerSqlSpecial(CustomerSearchBuilder fieldSearch) {
		StringBuilder sql = new StringBuilder("");
		if (StringUtils.isNoneBlank(fieldSearch.getCustomerId())) {
			sql.append(" AND U.customerId = " + fieldSearch.getCustomerId());
		}
		return sql.toString();
	}

//	@Override
//	public List<CustomerEntity> findById(Long id) {
//		List<CustomerEntity> result = new ArrayList<>();
//		String sql = "select * from customer where id = ? ";
//		Connection connection = null;
//		PreparedStatement statement = null;
//		ResultSet resultSet = null;
//		try {
//			connection = EntityManagerImpl.getConnection();
//			statement = connection.prepareStatement(sql);
//			statement.setLong(1, id);
//			resultSet = statement.executeQuery();
//			while (resultSet.next()) {
//				CustomerEntity customerEntity = new CustomerEntity();
//				customerEntity.setId((resultSet.getLong("id")));
//				result.add(customerEntity);
//			}
//			return result;
//		} catch (SQLException e) {
//			return new ArrayList<>();
//		} finally {
//			try {
//				if (connection != null) {
//					connection.close();
//				}
//				if (statement != null) {
//					statement.close();
//				}
//				if (resultSet != null) {
//					resultSet.close();
//				}
//			} catch (SQLException e) {
//				return new ArrayList<>();
//			}
//		}
//
//		String sql = "select * from customer where id = ?";
//		return this.findBy(sql, id);
//	}

}
