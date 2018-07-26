package com.elephantry.service;

import java.util.List;
import com.elephantry.entity.Package;

public interface PackageService {
	List<Package> findAll();
	Package findById(Integer id);
	int countbyId(Integer packageId);
}
