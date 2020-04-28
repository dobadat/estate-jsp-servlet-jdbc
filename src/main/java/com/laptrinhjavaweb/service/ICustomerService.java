package com.laptrinhjavaweb.service;

import java.util.List;

import com.laptrinhjavaweb.DTO.CustomerDTO;
import com.laptrinhjavaweb.builder.CustomerSearchBuilder;
import com.laptrinhjavaweb.paging.Pageable;

public interface ICustomerService {
	public List<CustomerDTO> findAll(CustomerSearchBuilder fieldSearch, Pageable pageable);

//	public List<CustomerDTO> findBy(Long id);
}
