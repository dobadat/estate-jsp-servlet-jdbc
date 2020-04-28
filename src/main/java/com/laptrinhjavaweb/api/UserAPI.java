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
import com.laptrinhjavaweb.DTO.UserDTO;
import com.laptrinhjavaweb.builder.BuilderSearchBuilder;
import com.laptrinhjavaweb.builder.UserSearchBuilder;
import com.laptrinhjavaweb.builder.UserSearchBuilder.Builder;
import com.laptrinhjavaweb.service.IUserService;
import com.laptrinhjavaweb.service.Impl.UserService;
import com.laptrinhjavaweb.util.FromUtil;
import com.laptrinhjavaweb.util.HttpUtil;

@WebServlet(urlPatterns = { "/api-user" })
public class UserAPI extends HttpServlet {
	private static final long serialVersionUID = -8390576278789890806L;
	private IUserService service;

	public UserAPI() {
		service = new UserService();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		UserDTO userDTO = HttpUtil.of(request.getReader()).toModel(UserDTO.class);
		userDTO = service.save(userDTO);
		mapper.writeValue(response.getOutputStream(), userDTO);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		UserDTO userDTO =   FromUtil.toModel(UserDTO.class, request);
		UserSearchBuilder userSearchBuilder = new UserSearchBuilder.Builder().setStatus(userDTO.getStatus()).setUserName(userDTO.getUserName()).build();
		List<UserDTO> listDTO = service.findUser(userSearchBuilder);
		mapper.writeValue(response.getOutputStream(), listDTO);
	}

}
