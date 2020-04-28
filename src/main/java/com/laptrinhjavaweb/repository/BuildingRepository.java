package com.laptrinhjavaweb.repository;

import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.builder.BuilderSearchBuilder;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.entity.RentAreaEntity;
import com.laptrinhjavaweb.entity.UserEntity;
import com.laptrinhjavaweb.paging.Pageable;

public interface BuildingRepository extends JpaRepository<BuildingEntity> {
	List<BuildingEntity> findAll(Map<String, Object> params, Pageable pageable, BuilderSearchBuilder fieldSearch);

	List<BuildingEntity> save(BuildingEntity buildingEntity);

	List<BuildingEntity> saveNew(BuildingEntity buildingEntity, BuilderSearchBuilder fieldSearch,
			RentAreaEntity areaEntity);

	BuildingEntity deleteID(BuildingEntity buildingEntity);

	BuildingEntity deleteString(BuilderSearchBuilder fieldSearch);

	BuildingEntity updateID(BuildingEntity buildingEntity);
	
	List<BuildingEntity> BuildingDeliverble(BuildingEntity buildingEntity);


}
