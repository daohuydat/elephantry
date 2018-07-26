package com.elephantry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elephantry.dao.ProvinceDAO;
import com.elephantry.entity.Province;
import com.elephantry.service.ProvinceService;

@Service
public class ProvinceServiceImpl implements ProvinceService{
	@Autowired
	private ProvinceDAO provinceDAO;

	@Override
	public List<Province> findByCountryId(String countryId) {
		try{
			return provinceDAO.findByCountryId(countryId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Province findById(Integer id) {
		try{
			return provinceDAO.findById(id);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
