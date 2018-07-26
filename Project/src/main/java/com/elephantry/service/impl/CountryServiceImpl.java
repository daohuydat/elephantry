package com.elephantry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elephantry.dao.CountryDAO;
import com.elephantry.entity.Country;
import com.elephantry.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryDAO countryDAO; 
	
	@Override
	public List<Country> findAll() {
		try{
			return countryDAO.findAll();	
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Country findById(String id) {
		try{
			return countryDAO.findById(id);	
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
