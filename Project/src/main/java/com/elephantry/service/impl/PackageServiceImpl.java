package com.elephantry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elephantry.dao.PackageDAO;
import com.elephantry.entity.Package;
import com.elephantry.service.PackageService;

@Service
public class PackageServiceImpl implements PackageService{

	@Autowired
	private PackageDAO packageDAO;
	
	@Override
	public List<Package> findAll() {
		try{
			return packageDAO.findAll();
		}catch(Exception e){
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public Package findById(Integer id) {
		try{
			return packageDAO.findById(id);
		}catch(Exception e){
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countbyId(Integer packageId) {
		try {
			return packageDAO.countbyId(packageId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	
}
