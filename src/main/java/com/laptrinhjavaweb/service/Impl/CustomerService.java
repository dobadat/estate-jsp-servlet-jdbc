package com.laptrinhjavaweb.service.Impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.laptrinhjavaweb.DTO.CustomerDTO;
import com.laptrinhjavaweb.builder.CustomerSearchBuilder;
import com.laptrinhjavaweb.converter.CustomerConverter;
import com.laptrinhjavaweb.entity.CustomerEntity;
import com.laptrinhjavaweb.paging.Pageable;
import com.laptrinhjavaweb.repository.CustomerRepository;
import com.laptrinhjavaweb.repository.impl.CustomerRepositoryImpl;
import com.laptrinhjavaweb.service.ICustomerService;

public class CustomerService implements ICustomerService {
	private CustomerRepository customerRepository = new CustomerRepositoryImpl();
	private CustomerConverter converter = new CustomerConverter();

	@Override
	public List<CustomerDTO> findAll(CustomerSearchBuilder fieldSearch, Pageable pageable) {
		Map<String, Object> property = convertToMapProperties(fieldSearch);
		List<CustomerEntity> customerEntity = customerRepository.findAllCustomer(property, pageable, fieldSearch);
		return customerEntity.stream().map(item -> converter.converToDTO(item)).collect(Collectors.toList());
	}

	private Map<String, Object> convertToMapProperties(CustomerSearchBuilder fieldSearch) {
		Map<String, Object> property = new HashMap<>();
		try {
			Field[] files = CustomerSearchBuilder.class.getDeclaredFields();
			for (Field field : files) {
				if (!field.getName().equals("customerId")) {
					field.setAccessible(true); // cho truy cap private
					if (field.get(fieldSearch) instanceof String) {
						if (field.get(fieldSearch) != null && StringUtils.isNotEmpty((String) field.get(fieldSearch))) {
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

//	@Override
//	public List<CustomerDTO> findBy(Long id) {
//		List<CustomerEntity> customerEntities = customerRepository.findById(id);
//		return customerEntities.stream().map(item -> converter.converToDTO(item)).collect(Collectors.toList());
//	}

}
