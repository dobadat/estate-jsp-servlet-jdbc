package com.laptrinhjavaweb.service;

import java.util.List;

import com.laptrinhjavaweb.DTO.UserDTO;
import com.laptrinhjavaweb.builder.UserSearchBuilder;

public interface IUserService {
	UserDTO save(UserDTO dto);
	
	List<UserDTO> findUser(UserDTO dto);
	List<UserDTO> findUser(UserSearchBuilder dto);
	
}
