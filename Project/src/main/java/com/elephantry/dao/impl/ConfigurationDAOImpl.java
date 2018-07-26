package com.elephantry.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.elephantry.dao.AbstractGenericDAO;
import com.elephantry.dao.ConfigurationDAO;
import com.elephantry.entity.Configuration;

@Repository
@Transactional
public class ConfigurationDAOImpl extends AbstractGenericDAO<Configuration, String> implements ConfigurationDAO  {

}
