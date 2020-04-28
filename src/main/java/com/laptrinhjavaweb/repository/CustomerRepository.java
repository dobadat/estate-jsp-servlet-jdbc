package com.laptrinhjavaweb.repository;

import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.builder.CustomerSearchBuilder;
import com.laptrinhjavaweb.entity.CustomerEntity;
import com.laptrinhjavaweb.paging.Pageable;

public interface CustomerRepository extends JpaRepository<CustomerEntity> {
	List<CustomerEntity> findAllCustomer(Map<String, Object> params, Pageable pageable,
			CustomerSearchBuilder fieldSearch);

//	List<CustomerEntity> findById(Long id);

}
