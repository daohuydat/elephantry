package com.elephantry.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.elephantry.dao.AbstractGenericDAO;
import com.elephantry.dao.CountryDAO;
import com.elephantry.entity.Country;

@Repository
@Transactional
public class CountryDAOImpl extends AbstractGenericDAO<Country, String> implements CountryDAO {

}
