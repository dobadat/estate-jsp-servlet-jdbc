package com.laptrinhjavaweb.converter;

import org.modelmapper.ModelMapper;

import com.laptrinhjavaweb.DTO.BuildingDTO;
import com.laptrinhjavaweb.DTO.RentAreaDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.entity.RentAreaEntity;

public class RentAreaConverter {

	public RentAreaDTO converToDTO(RentAreaEntity entity) {
		ModelMapper modelMapper = new ModelMapper();
		RentAreaDTO dto = modelMapper.map(entity, RentAreaDTO.class);
		return dto;
	}
	
	public RentAreaEntity convertToEntity(RentAreaDTO buildingDTO) {
		ModelMapper modelMapper = new ModelMapper();
		RentAreaEntity dto = modelMapper.map(buildingDTO, RentAreaEntity.class);
		return dto;
	}
}
