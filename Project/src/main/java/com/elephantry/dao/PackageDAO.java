package com.elephantry.dao;

import java.util.List;

import com.elephantry.entity.Package;

public interface PackageDAO extends GenericDAO<Package, Integer> {
	int countbyId(int packageId);
}
