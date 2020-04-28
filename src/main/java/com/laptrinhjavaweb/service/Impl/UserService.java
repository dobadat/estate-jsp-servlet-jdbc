package com.laptrinhjavaweb.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.laptrinhjavaweb.DTO.UserDTO;
import com.laptrinhjavaweb.builder.UserSearchBuilder;
import com.laptrinhjavaweb.converter.UserConverter;
import com.laptrinhjavaweb.entity.UserEntity;
import com.laptrinhjavaweb.repository.UserRepository;
import com.laptrinhjavaweb.repository.impl.UserRepositoryImpl;
import com.laptrinhjavaweb.service.IUserService;

public class UserService implements IUserService {

	private UserConverter userConverter;
	private UserRepository userRepository;

	public UserService() {
		userConverter = new UserConverter();
		userRepository = new UserRepositoryImpl();
	}

	@Override
	public UserDTO save(UserDTO dto) {
		UserEntity entity = userConverter.convertToEntity(dto);
		return userConverter.converToDTO(userRepository.save(entity));
	}

	@Override
	public List<UserDTO> findUser(UserDTO dto) {
		UserEntity entity = userConverter.convertToEntity(dto);
		List<UserEntity> entityes = userRepository.findUser(entity);
		List<UserDTO> results = new ArrayList<>();
		for (UserEntity buildingEntity : entityes) {
				UserDTO buildingDTO = userConverter.converToDTO(buildingEntity);
				results.add(buildingDTO);
		}
		return results;
//		return entityes.stream().map(item -> userConverter.converToDTO(item)).collect(Collectors.toList());
	}

	@Override
	public List<UserDTO> findUser(UserSearchBuilder dto) {
		List<UserEntity> entityes = userRepository.findUser(dto);
		return entityes.stream().map(item -> userConverter.converToDTO(item)).collect(Collectors.toList());
	}

}
