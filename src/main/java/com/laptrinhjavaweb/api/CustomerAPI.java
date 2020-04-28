package com.laptrinhjavaweb.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptrinhjavaweb.DTO.CustomerDTO;
import com.laptrinhjavaweb.builder.CustomerSearchBuilder;
import com.laptrinhjavaweb.paging.PageResquest;
import com.laptrinhjavaweb.paging.Pageable;
import com.laptrinhjavaweb.service.ICustomerService;
import com.laptrinhjavaweb.service.Impl.CustomerService;
import com.laptrinhjavaweb.util.FromUtil;

@WebServlet(urlPatterns = { "/api-customer" })
public class CustomerAPI extends HttpServlet {
	private static final long serialVersionUID = -915988021506484384L;
	private ICustomerService customerService = new CustomerService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		CustomerDTO customerDTO = FromUtil.toModel(CustomerDTO.class, request);
		CustomerSearchBuilder customerSearchBuilder = new CustomerSearchBuilder.Builder()
				.setCustomerName(customerDTO.getCustomerName()).setEmail(customerDTO.getEmail())
				.setPhoneNumber(customerDTO.getPhoneNumber()).setCustomerId(customerDTO.getCustomerId()).build();
		Pageable pageable = new PageResquest(customerDTO.getPage(), customerDTO.getLimit());
		List<CustomerDTO> customers = customerService.findAll(customerSearchBuilder, pageable);
//		List<CustomerDTO> customerDTOs = customerService.findBy(customerDTO.getId());
		mapper.writeValue(response.getOutputStream(), customers);
	

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
