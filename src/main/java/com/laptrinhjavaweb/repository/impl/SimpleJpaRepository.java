package com.laptrinhjavaweb.repository.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.laptrinhjavaweb.annotation.Column;
import com.laptrinhjavaweb.annotation.Entity;
import com.laptrinhjavaweb.annotation.Table;
import com.laptrinhjavaweb.mapper.ResultSetMapper;
import com.laptrinhjavaweb.paging.Pageable;
import com.laptrinhjavaweb.repository.JpaRepository;

public class SimpleJpaRepository<T> implements JpaRepository<T> {
	private Class<T> zClass;
//	private IMapSet map = new MapSet();

	@SuppressWarnings("unchecked")
	public SimpleJpaRepository() {
		Type type = getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) type;
		zClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	@Override
	public List<T> findAll(Map<String, Object> params, Pageable pageable, Object... where) {
		String tableName = "";
		if (zClass.isAnnotationPresent(Entity.class) && zClass.isAnnotationPresent(Table.class)) {
			Table tableClass = zClass.getAnnotation(Table.class);
			tableName = tableClass.name();
		}
		StringBuilder sql = new StringBuilder("select * from " + tableName + " A ");
		sql.append(" where 1=1 ");
		sql = createSQLfindAll(sql, params);
		if (where != null && where.length > 0) {
			sql.append(where[0]);
		}
		sql.append(" limit " + pageable.getOffset() + ", " + pageable.getLimit() + "");
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = EntityManagerImpl.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql.toString());
			return resultSetMapper.mapRow(resultSet, this.zClass);
		} catch (SQLException e) {
			return new ArrayList<>();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				return new ArrayList<>();
			}
		}
	}

	protected StringBuilder createSQLfindAll(StringBuilder where, Map<String, Object> params) {
		if (params != null && params.size() > 0) {
			String[] keys = new String[params.size()];
			Object[] values = new Object[params.size()];
			int i = 0;
			for (Map.Entry<String, Object> item : params.entrySet()) {
				keys[i] = item.getKey();
				values[i] = item.getValue();
				i++;
			}
			for (int i1 = 0; i1 < keys.length; i1++) {
				if ((values[i1] instanceof String) && (StringUtils.isNotBlank(values[i1].toString()))) {
					where.append(" AND LOWER(A." + keys[i1] + ") LIKE '%" + values[i1].toString() + "%' ");
				} else if ((values[i1] instanceof Integer) && values[i1] != null) {
					where.append(" AND LOWER(A." + keys[i1] + ") =" + values[i1].toString());
				} else if ((values[i1] instanceof Long) && values[i1] != null) {
					where.append(" AND LOWER(A." + keys[i1] + ") =" + values[i1].toString());
				}
			}
		}
		return where;
	}

	@Override
	public List<T> findAll(Map<String, Object> params, Object... where) {
		String tableName = "";
		if (zClass.isAnnotationPresent(Entity.class) && zClass.isAnnotationPresent(Table.class)) {
			Table tableClass = zClass.getAnnotation(Table.class);
			tableName = tableClass.name();
		}
		StringBuilder sql = new StringBuilder("select * from " + tableName + " A  where 1=1 ");
		sql = createSQLfindAll(sql, params);
		if (where != null && where.length > 0) {
			sql.append(where[0]);
		}
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = EntityManagerImpl.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql.toString());
			return resultSetMapper.mapRow(resultSet, this.zClass);
		} catch (SQLException e) {
			return new ArrayList<>();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				return new ArrayList<>();
			}
		}
	}

	@Override
	public List<T> findAll(String sqlSearch, Pageable pageable, Object... where) {
		StringBuilder sql = new StringBuilder(sqlSearch);
		sql.append(" limit " + pageable.getOffset() + ", " + pageable.getLimit() + "");
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = EntityManagerImpl.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql.toString());
			return resultSetMapper.mapRow(resultSet, this.zClass);
		} catch (SQLException e) {
			return new ArrayList<>();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				return new ArrayList<>();
			}
		}
	}

	@Override
	public List<T> findAll(String sqlSearch, Object... where) {
		StringBuilder sql = new StringBuilder(sqlSearch);
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = EntityManagerImpl.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql.toString());
			return resultSetMapper.mapRow(resultSet, this.zClass);
		} catch (SQLException e) {
			return new ArrayList<>();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				return new ArrayList<>();
			}
		}
	}

	@Override
	public Long insert(Object object) {
		String sql = createSQLInsert();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			Long id = null;
			connection = EntityManagerImpl.getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			Class<?> aClass = object.getClass();
			Field[] fields = aClass.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				int index = i + 1;
				Field field = fields[i];
				field.setAccessible(true);
				statement.setObject(index, field.get(object));
			}
			Class<?> parentClass = aClass.getSuperclass();
			int indexParent = fields.length + 1;
			while (parentClass != null) {
				Field[] fieldParent = parentClass.getDeclaredFields();
				for (int i = 0; i < fieldParent.length; i++) {
					Field field = fieldParent[i];
					field.setAccessible(true);
					statement.setObject(indexParent, field.get(object));
					indexParent++;
				}
				parentClass = parentClass.getSuperclass();
			}
			statement.executeUpdate();
			// khi update xong, se lay ra id moi update xong
			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}
			connection.commit();
			return id;
		} catch (SQLException | IllegalAccessException e) {
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
		return null;
	}

	protected String createSQLInsert() {
		String tableName = "";
		if (zClass.isAnnotationPresent(Entity.class) && zClass.isAnnotationPresent(Table.class)) {
			Table tableClass = zClass.getAnnotation(Table.class);
			tableName = tableClass.name();
		}
		StringBuilder fields = new StringBuilder("");
		StringBuilder params = new StringBuilder("");

		for (Field field : zClass.getDeclaredFields()) {
			if (fields.length() > 1) {
				fields.append(",");
				params.append(",");
			}
			if (field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				fields.append(column.name());
				params.append("?");
			}
		}
		Class<?> parentClass = zClass.getSuperclass();
		while (parentClass != null) {
			for (Field field : parentClass.getDeclaredFields()) {
				if (fields.length() > 1) {
					fields.append(",");
					params.append(",");
				}
				if (field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					fields.append(column.name());
					params.append("?");
				}
			}
			parentClass = parentClass.getSuperclass();
		}
		String sql = "INSERT INTO " + tableName + "(" + fields + ")" + "VALUES(" + params + ")";
		return sql;
	}

	@Override
	public void delete(Long id) {
		String tableName = "";
		if (zClass.isAnnotationPresent(Entity.class) && zClass.isAnnotationPresent(Table.class)) {
			Table tableClass = zClass.getAnnotation(Table.class);
			tableName = tableClass.name();
		}
		StringBuilder sql = new StringBuilder("DELETE FROM " + tableName + " WHERE 1=1 ");
		sql.append(" AND id= ?");
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = EntityManagerImpl.getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql.toString());
			statement.setLong(1, id);
			statement.executeUpdate();
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

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findBy(Long id) {
		String tableName = "";
		if (zClass.isAnnotationPresent(Entity.class) && zClass.isAnnotationPresent(Table.class)) {
			Table tableClass = zClass.getAnnotation(Table.class);
			tableName = tableClass.name();
		}
		List<T> result = new ArrayList<>();
		String sql = "select * from " + tableName + " where id = ? ";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = EntityManagerImpl.getConnection();
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setObject(1, id);
			resultSet = statement.executeQuery();
			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				result.add((T) resultSet.getObject("id"));
			}
			return result;
		} catch (SQLException | IllegalArgumentException e) {
			return null;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				return null;
			}
		}
	}

	@Override
	public Long update(Object object) {
		String sql = createSQLUpdate();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Object objectId = new Object();
		try {
			Long id = null;
			connection = EntityManagerImpl.getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			Class<?> aClass = object.getClass();
			Field[] fields = aClass.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				int index = i + 1;
				Field field = fields[i];
				field.setAccessible(true); // tai sap lai setAccessible ???
				statement.setObject(index, field.get(object));
			}
			Class<?> parentClass = aClass.getSuperclass();
			int indexParent = fields.length + 1;// 22
			while (parentClass != null) {
				Field[] fieldParent = parentClass.getDeclaredFields();
				for (int i = 0; i < fieldParent.length; i++) {
					Field field = fieldParent[i];
					field.setAccessible(true); // tai sap lai setAccessible ???
					if (field.isAnnotationPresent(Column.class)) {
						Column column = field.getAnnotation(Column.class);
						if (!column.name().equals("id")) {
							statement.setObject(indexParent, field.get(object));
							indexParent++;
						}
						if (column.name().equals("id")) {
							objectId = field.get(object);
						}
					}
				}
				parentClass = parentClass.getSuperclass();
			}
			statement.setObject(indexParent, objectId);
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}
			connection.commit();
			return id;
		} catch (SQLException | IllegalAccessException e) {
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
		return null;

	}

	private String createSQLUpdate() {
		String tableName = "";
		if (zClass.isAnnotationPresent(Entity.class) && zClass.isAnnotationPresent(Table.class)) {
			Table tableClass = zClass.getAnnotation(Table.class);
			tableName = tableClass.name();
		}
		StringBuilder fields = new StringBuilder("");
		for (Field field : zClass.getDeclaredFields()) {
			if (fields.length() > 0) {
				fields.append(" , ");
			}
			if (field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				fields.append(column.name() + " = ? ");
			}
		}
		Class<?> parentClass = zClass.getSuperclass();
		while (parentClass != null) {
			for (Field field : parentClass.getDeclaredFields()) {
				if (fields.length() > 0) {
					fields.append(" , ");
				}
				if (field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					fields.append(column.name() + " = ? ");
				}
			}
			parentClass = parentClass.getSuperclass();
		}
		String sql = "UPDATE " + tableName + " SET " + fields + " WHERE id = ?";
		String newSql = sql.replace(", id = ?", "");
		return newSql;
	}

	@Override
	public void delete(String sql) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = EntityManagerImpl.getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			statement.executeUpdate();
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

	@Override
	public Long insert(Object object, Object... where) {
		String sql = createSQLInsert();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			Long id = null;
			connection = EntityManagerImpl.getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			Class<?> aClass = object.getClass();
			Field[] fields = aClass.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				int index = i + 1;
				Field field = fields[i];
				field.setAccessible(true);
				if (where != null && where.length > 0) {
					StringBuilder types =  (StringBuilder) where[0];
					if (field.isAnnotationPresent(Column.class)) {
						Column column = field.getAnnotation(Column.class);
						if (column.name().equals("type")) {
							statement.setObject(index, types.toString());
						}else {
							statement.setObject(index, field.get(object));
						}
					}
				}
			}
			Class<?> parentClass = aClass.getSuperclass();
			int indexParent = fields.length + 1;
			while (parentClass != null) {
				Field[] fieldParent = parentClass.getDeclaredFields();
				for (int i = 0; i < fieldParent.length; i++) {
					Field field = fieldParent[i];
					field.setAccessible(true);
					statement.setObject(indexParent, field.get(object));
					indexParent++;
				}
				parentClass = parentClass.getSuperclass();
			}
			statement.executeUpdate();
			// khi update xong, se lay ra id moi update xong
			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}
			connection.commit();
			return id;
		} catch (SQLException | IllegalAccessException e) {
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
		return null;
	}

}
// số dòng code dài ? cách giải quyết