package com.laptrinhjavaweb.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptrinhjavaweb.DTO.BuildingDTO;
import com.laptrinhjavaweb.DTO.RentAreaDTO;
import com.laptrinhjavaweb.builder.BuilderSearchBuilder;
import com.laptrinhjavaweb.paging.PageResquest;
import com.laptrinhjavaweb.paging.Pageable;
import com.laptrinhjavaweb.service.IBuildingService;
import com.laptrinhjavaweb.service.Impl.BuildingService;
import com.laptrinhjavaweb.util.FromUtil;
import com.laptrinhjavaweb.util.HttpUtil;

@WebServlet(urlPatterns = { "/api-building" })
public class BuildingAPI extends HttpServlet {
	private static final long serialVersionUID = -915988021506484384L;
	private IBuildingService buildingService = new BuildingService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		BuildingDTO buildingDTO = FromUtil.toModel(BuildingDTO.class, request);
		BuilderSearchBuilder builderSearchBuilder = new BuilderSearchBuilder.Builder().setName(buildingDTO.getName())
				.setDistrict(buildingDTO.getDistrict()).setBuildingArea(buildingDTO.getBuildingArea())
				.setStreet(buildingDTO.getStreet()).setWard(buildingDTO.getWard())
				.setNumberOfBasement(buildingDTO.getNumberOfBasement()).setBuildingTypes(buildingDTO.getBuildingTypes())
				.setAreaRentFrom(buildingDTO.getAreaRentFrom()).setAreaRentTo(buildingDTO.getAreaRentTo())
				.setCostRentFrom(buildingDTO.getCostRentFrom()).setCostRentTo(buildingDTO.getCostRentTo())
				.setStaffId(buildingDTO.getStaffId()).build();
		Pageable pageable = new PageResquest(buildingDTO.getPage(), buildingDTO.getLimit());
		List<BuildingDTO> buildings = buildingService.findAll(builderSearchBuilder, pageable);
		mapper.writeValue(response.getOutputStream(), buildings);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		BuildingDTO buildingDTO = HttpUtil.of(request.getReader()).toModel(BuildingDTO.class);
		List<BuildingDTO> buildingDTOs = new ArrayList<>();
		BuilderSearchBuilder builderSearchBuilder = new BuilderSearchBuilder.Builder().setName(buildingDTO.getName())
				.setDistrict(buildingDTO.getDistrict()).setBuildingArea(buildingDTO.getBuildingArea())
				.setStreet(buildingDTO.getStreet()).setWard(buildingDTO.getWard())
				.setNumberOfBasement(buildingDTO.getNumberOfBasement()).setBuildingTypes(buildingDTO.getBuildingTypes())
				.setAreaRentFrom(buildingDTO.getAreaRentFrom()).setAreaRentTo(buildingDTO.getAreaRentTo())
				.setCostRentFrom(buildingDTO.getCostRentFrom()).setCostRentTo(buildingDTO.getCostRentTo())
				.setStaffId(buildingDTO.getStaffId()).setAreaRent(buildingDTO.getAreaRent()).build();
		RentAreaDTO areaDTO = new RentAreaDTO();
		buildingDTOs = buildingService.saveNew(buildingDTO, builderSearchBuilder, areaDTO);
		mapper.writeValue(response.getOutputStream(), buildingDTOs);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		BuildingDTO buildingDTO = HttpUtil.of(request.getReader()).toModel(BuildingDTO.class);
//		buildingDTO = buildingService.deleteID(buildingDTO);
		BuilderSearchBuilder builderSearchBuilder = new BuilderSearchBuilder.Builder().setName(buildingDTO.getName())
				.setDistrict(buildingDTO.getDistrict()).setBuildingArea(buildingDTO.getBuildingArea())
				.setStreet(buildingDTO.getStreet()).setWard(buildingDTO.getWard())
				.setNumberOfBasement(buildingDTO.getNumberOfBasement()).setBuildingTypes(buildingDTO.getBuildingTypes())
				.setAreaRentFrom(buildingDTO.getAreaRentFrom()).setAreaRentTo(buildingDTO.getAreaRentTo())
				.setCostRentFrom(buildingDTO.getCostRentFrom()).setCostRentTo(buildingDTO.getCostRentTo())
				.setStaffId(buildingDTO.getStaffId()).build();
		buildingDTO = buildingService.deleteString(builderSearchBuilder);
		mapper.writeValue(response.getOutputStream(), buildingDTO);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		BuildingDTO buildingDTO = HttpUtil.of(request.getReader()).toModel(BuildingDTO.class);
		buildingDTO = buildingService.updateID(buildingDTO);
		mapper.writeValue(response.getOutputStream(), buildingDTO);
	}
}
