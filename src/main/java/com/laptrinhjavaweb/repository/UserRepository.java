package com.laptrinhjavaweb.repository;

import java.util.List;

import com.laptrinhjavaweb.builder.UserSearchBuilder;
import com.laptrinhjavaweb.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity> {
	UserEntity save(UserEntity entity);
	List<UserEntity> findUser(UserEntity entity );
	List<UserEntity> findUser(UserSearchBuilder entity );

}
