package com.laptrinhjavaweb.converter;

import org.modelmapper.ModelMapper;

import com.laptrinhjavaweb.DTO.BuildingDTO;
import com.laptrinhjavaweb.DTO.CustomerDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.entity.CustomerEntity;

public class BuildingConverter {
	public BuildingDTO converToDTO(BuildingEntity entity) {
		ModelMapper modelMapper = new ModelMapper();
		BuildingDTO dto = modelMapper.map(entity, BuildingDTO.class);
		if (entity.getBuildingArea() != null) {
			dto.setBuildingArea(String.valueOf(entity.getBuildingArea()));
		}
		if (entity.getNumberOfBasement() != null) {
			dto.setNumberOfBasement(String.valueOf(entity.getNumberOfBasement()));
		}
		return dto;
	}
	
	public BuildingEntity convertToEntity(BuildingDTO buildingDTO) {
		ModelMapper modelMapper = new ModelMapper();
		BuildingEntity dto = modelMapper.map(buildingDTO, BuildingEntity.class);
		return dto;
	}
}
