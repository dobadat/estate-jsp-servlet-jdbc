package com.laptrinhjavaweb.service;

import java.util.List;

import com.laptrinhjavaweb.DTO.BuildingDTO;
import com.laptrinhjavaweb.DTO.RentAreaDTO;
import com.laptrinhjavaweb.builder.BuilderSearchBuilder;
import com.laptrinhjavaweb.entity.RentAreaEntity;
import com.laptrinhjavaweb.paging.Pageable;

public interface IBuildingService {
	public List<BuildingDTO> findAll(BuilderSearchBuilder fieldSearch, Pageable pageable);

	List<BuildingDTO> save(BuildingDTO buildingDTO);

	List<BuildingDTO> saveNew(BuildingDTO buildingDTO,BuilderSearchBuilder fieldSearch,
			RentAreaDTO areaDTO);

	BuildingDTO deleteID(BuildingDTO buildingDTO);
	
	BuildingDTO deleteString(BuilderSearchBuilder fieldSearch);
	
	BuildingDTO updateID(BuildingDTO buildingDTO);

}
