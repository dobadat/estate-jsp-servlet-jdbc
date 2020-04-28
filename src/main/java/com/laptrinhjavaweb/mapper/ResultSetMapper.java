package com.laptrinhjavaweb.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.laptrinhjavaweb.annotation.Column;
import com.laptrinhjavaweb.annotation.Entity;

public class ResultSetMapper<T> {
	public List<T> mapRow(ResultSet rs, Class<T> zClass) {
		List<T> results = new ArrayList<>();
		try {
			if (zClass.isAnnotationPresent(Entity.class)) {
				ResultSetMetaData resultSetMetaData = rs.getMetaData();
				Field[] fields = zClass.getDeclaredFields();
				while (rs.next()) {
					/*
					 * Tạo Object T là khởi tạo 1 đối tượng của zClass Nếu zClass là BuildingEntity
					 * mà trong class BuildingEntity ko có contructor Vì vậy zClass.newInstance ra
					 * sẽ tạo ra đối tượng cũng là BuildingEntity nhưng nó sẽ có cấu trúc của
					 * contructor Để lưu trữ.
					 */
					T object = zClass.newInstance();
					for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
						String columnName = resultSetMetaData.getColumnName(i + 1);
						Object columnValue = rs.getObject(i + 1);
						ColumnModel columnModel = new ColumnModel();
						columnModel.setColumnName(columnName);
						columnModel.setColumValue(columnValue);
						convertResultSetToEntity(fields, columnModel, object);
						Class<?> parentsClass = zClass.getSuperclass();
						while (parentsClass != null) {
							Field[] fieldParents = parentsClass.getDeclaredFields();
							convertResultSetToEntity(fieldParents,columnModel, object);
							parentsClass = parentsClass.getSuperclass();
						}
					}
					results.add(object);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return results;

	}

	@SuppressWarnings("unchecked")
	private void convertResultSetToEntity(Field[] fields,ColumnModel columnModel ,Object...objects) {
		T object =(T) objects[0];
		for (Field field : fields) {
			if (field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				if (column.name().equals(columnModel.getColumnName()) && columnModel.getColumValue() != null) {
					try {
						BeanUtils.setProperty(object, columnModel.getColumnName(), columnModel.getColumValue());
					} catch (IllegalAccessException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}

			}
		}
	}

	static class ColumnModel {
		private String columnName;
		private Object columValue;

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		public Object getColumValue() {
			return columValue;
		}

		public void setColumValue(Object columValue) {
			this.columValue = columValue;
		}

	}
}
