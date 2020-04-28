package com.laptrinhjavaweb.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

public class FromUtil {

	public static <T> T toModel(Class<T> Zclass, HttpServletRequest request) {
		T object = null;
		try {
			object = Zclass.newInstance();
			BeanUtils.populate(object, request.getParameterMap());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return object;
	}
}
