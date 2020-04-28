package com.laptrinhjavaweb.converter;

import org.modelmapper.ModelMapper;

import com.laptrinhjavaweb.DTO.UserDTO;
import com.laptrinhjavaweb.entity.UserEntity;

public class UserConverter {

	public UserDTO converToDTO(UserEntity entity) {
		ModelMapper modelMapper = new ModelMapper();
		UserDTO dto = modelMapper.map(entity, UserDTO.class);
		return dto;
	}
	
	public UserEntity convertToEntity(UserDTO userDTO) {
		ModelMapper modelMapper = new ModelMapper();
		UserEntity dto = modelMapper.map(userDTO, UserEntity.class);
		return dto;
	}
}
