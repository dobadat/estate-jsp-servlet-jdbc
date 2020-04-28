package com.laptrinhjavaweb.service.Impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.laptrinhjavaweb.DTO.BuildingDTO;
import com.laptrinhjavaweb.DTO.RentAreaDTO;
import com.laptrinhjavaweb.builder.BuilderSearchBuilder;
import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.converter.RentAreaConverter;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.entity.RentAreaEntity;
import com.laptrinhjavaweb.paging.Pageable;
import com.laptrinhjavaweb.repository.BuildingRepository;
import com.laptrinhjavaweb.repository.impl.BuildingRepositoryImpl;
import com.laptrinhjavaweb.service.IBuildingService;

public class BuildingService implements IBuildingService {

	private BuildingRepository buildingRepository = new BuildingRepositoryImpl();
	private BuildingConverter buildingConverter = new BuildingConverter();
	private RentAreaConverter rentAreaConverter = new RentAreaConverter();

	@Override
	public List<BuildingDTO> findAll(BuilderSearchBuilder fieldSearch, Pageable pageable) {
		// java 7
//		List<Buildi ngDTO> results = new ArrayList<>();
//		List<BuildingEntity> buildingEntities = buildingRepository.findAll();
//		for (BuildingEntity buildingEntity : buildingEntities) {
//			BuildingDTO buildingDTO = buildingConverter.converToDTO(buildingEntity);
//			results.add(buildingDTO);
//		}
//		return results;
		// java 8
		Map<String, Object> property = convertToMapProperties(fieldSearch);
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(property, pageable, fieldSearch);
		return buildingEntities.stream().map(item -> buildingConverter.converToDTO(item)).collect(Collectors.toList());

	}

	private Map<String, Object> convertToMapProperties(BuilderSearchBuilder fieldSearch) {
		Map<String, Object> property = new HashMap<>();
		try {
			Field[] files = BuilderSearchBuilder.class.getDeclaredFields();
			for (Field field : files) {
				if (!field.getName().equals("buildingTypes") && !field.getName().startsWith("costRent")
						&& !field.getName().startsWith("areaRent") && !field.getName().equals("staffId")) {
					field.setAccessible(true); // cho truy cap private
					if (field.get(fieldSearch) instanceof String) {
						if (field.getName().equals("buildingArea") || field.getName().equals("numberOfBasement")) {
							if (field.get(fieldSearch) != null
									&& StringUtils.isNotEmpty((String) field.get(fieldSearch))) {
								property.put(field.getName().toLowerCase(),
										Integer.parseInt((String) field.get(fieldSearch)));
							}
						} else {
							property.put(field.getName().toLowerCase(), field.get(fieldSearch));
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return property;
	}

	@Override
	public List<BuildingDTO> save(BuildingDTO buildingDTO) {
		// khi dữ liệu ở DTO . cần phải chuyển DTO -> ENTITY để thực hiện hàm save, rùi
		// chuyển lại DTO để hiện ra ngoài.
		BuildingEntity entity = buildingConverter.convertToEntity(buildingDTO);
		entity.setCreatedDate(new Date());
		entity.setCreatedBy("BY");
//		Long id = buildingRepository.insert(entity);
		List<BuildingEntity> resutlEntity = new ArrayList<>();
		resutlEntity = buildingRepository.save(entity);
		List<BuildingDTO> resutlDTO = new ArrayList<>();
		resutlEntity.add(entity);
		buildingDTO = buildingConverter.converToDTO(entity);
		resutlDTO.add(buildingDTO);
		return resutlDTO;
	}

	@Override
	public BuildingDTO deleteID(BuildingDTO buildingDTO) {
		BuildingEntity buildingEntity = buildingConverter.convertToEntity(buildingDTO);
		buildingEntity = buildingRepository.deleteID(buildingEntity);
		return null;
	}

	@Override
	public BuildingDTO updateID(BuildingDTO buildingDTO) {
		BuildingEntity buildingEntity = buildingConverter.convertToEntity(buildingDTO);
		buildingEntity = buildingRepository.updateID(buildingEntity);
		return null;
	}

//Map<String, Object> params,BuilderSearchBuilder fieldSearch
	@Override
	public BuildingDTO deleteString(BuilderSearchBuilder fieldSearch) {
		BuildingEntity buildingEntity = buildingRepository.deleteString(fieldSearch);
		return null;
	}

	@Override
	public List<BuildingDTO> saveNew(BuildingDTO buildingDTO, BuilderSearchBuilder fieldSearch, RentAreaDTO areaDTO) {
		BuildingEntity entity = buildingConverter.convertToEntity(buildingDTO);
		RentAreaEntity areaEntity = rentAreaConverter.convertToEntity(areaDTO);
		entity.setCreatedDate(new Date());
		entity.setCreatedBy("BY");
//		Long id = buildingRepository.insert(entity);
		List<BuildingEntity> resutlEntity = new ArrayList<>();
		resutlEntity = buildingRepository.saveNew(entity, fieldSearch, areaEntity);
//		List<BuildingDTO> resutlDTO = new ArrayList<>();
//		resutlEntity.add(entity);
//		buildingDTO = buildingConverter.converToDTO(entity);
//		resutlDTO.add(buildingDTO);
		return null;
	}
}
