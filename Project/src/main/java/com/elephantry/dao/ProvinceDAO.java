package com.elephantry.dao;

import java.util.List;

import com.elephantry.entity.Province;

public interface ProvinceDAO extends GenericDAO<Province, Integer> {
	List<Province> findByCountryId(String countryId);
}
