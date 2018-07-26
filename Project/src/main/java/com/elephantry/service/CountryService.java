package com.elephantry.service;

import java.util.List;

import com.elephantry.entity.Country;

public interface CountryService {
	List<Country> findAll();
	Country findById(String id);
}
