package com.elephantry.dao.impl;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.elephantry.dao.AbstractGenericDAO;
import com.elephantry.dao.PackageDAO;
import com.elephantry.entity.Package;

@Repository
@Transactional
public class PackageDAOImpl extends AbstractGenericDAO<Package, Integer> implements PackageDAO {

	@Override
	public int countbyId(int packageId) {
		Query query = getSession().createSQLQuery("select count(*) from customer where packageId = :packageId ");
		query.setInteger("packageId", packageId);
		Number result = (Number) query.uniqueResult();
		return result==null?0:result.intValue();
	}
	
}
