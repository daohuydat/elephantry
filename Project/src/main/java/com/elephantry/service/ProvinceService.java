package com.elephantry.service;

import java.util.List;

import com.elephantry.entity.Province;

public interface ProvinceService {
	List<Province> findByCountryId(String countryId);
	Province findById(Integer id);
}
