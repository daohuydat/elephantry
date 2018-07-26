package com.elephantry.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.elephantry.dao.AbstractGenericDAO;

import com.elephantry.dao.ProvinceDAO;
import com.elephantry.entity.Province;

@Repository
@Transactional
public class ProvinceDAOImpl extends AbstractGenericDAO<Province, Integer> implements ProvinceDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Province> findByCountryId(String countryId) {
		
		Query query = getSession().createQuery("from province where CountryId = :countryId  ");
		query.setString("countryId", countryId);
		return query.list();
	}
	
}
