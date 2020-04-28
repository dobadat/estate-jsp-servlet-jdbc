package com.laptrinhjavaweb.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.laptrinhjavaweb.builder.UserSearchBuilder;
import com.laptrinhjavaweb.entity.UserEntity;
import com.laptrinhjavaweb.repository.UserRepository;

public class UserRepositoryImpl extends SimpleJpaRepository<UserEntity> implements UserRepository {

	@Override
	public UserEntity save(UserEntity entity) {
		String sql = "insert into user(username,fullname) values (?,?) ";
		Connection connection = null;
		PreparedStatement statement = null;
//		ResultSet resultSet = null;
		try {
			Long id = null;
			connection = EntityManagerImpl.getConnection();
			connection.setAutoCommit(false);
//			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // Statement.RETURN_GENERATED_KEYS : want to return id tu dong
			statement = connection.prepareStatement(sql);
			statement.setString(1, entity.getUserName());
			statement.setString(2, entity.getFullName());
			statement.executeUpdate();
			connection.commit();
			return entity;
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
		return new UserEntity();
	}

	@Override
	public List<UserEntity> findUser(UserEntity entity) {
		String sql = "select * from user where status =" + entity.getStatus();
		return this.findAll(sql);
	}

	@Override
	public List<UserEntity> findUser(UserSearchBuilder entity) {
		String sql = "";
		if (entity.getStatus() != null) {
			sql = "select id,username,checked from user where status = " + entity.getStatus();
		}
		return this.findAll(sql);
	}
}
