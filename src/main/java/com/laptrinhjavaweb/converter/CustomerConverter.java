package com.laptrinhjavaweb.converter;

import org.modelmapper.ModelMapper;

import com.laptrinhjavaweb.DTO.CustomerDTO;
import com.laptrinhjavaweb.entity.CustomerEntity;

public class CustomerConverter {
	public CustomerDTO converToDTO(CustomerEntity entity) {
		ModelMapper modelMapper = new ModelMapper();
		CustomerDTO cdto = modelMapper.map(entity, CustomerDTO.class);
		return cdto;
	}
}
