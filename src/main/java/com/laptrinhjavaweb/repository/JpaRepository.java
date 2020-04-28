package com.laptrinhjavaweb.repository;

import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.paging.Pageable;

public interface JpaRepository<T> {
	public List<T> findAll(Map<String, Object> params, Pageable pageable, Object... where);

	public List<T> findAll(Map<String, Object> params, Object... where);

	public List<T> findAll(String sql, Pageable pageable, Object... where);

	public List<T> findAll(String sql, Object... where);

	List<T> findBy(Long id);

	Long insert(Object object);
	
	Long insert(Object object,Object... where);

	void delete(Long id);

	void delete(String sql);

	Long update(Object object);
}
