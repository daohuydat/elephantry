package com.elephantry.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elephantry.dao.ConfigurationDAO;
import com.elephantry.entity.Configuration;
import com.elephantry.entity.Recommendation;
import com.elephantry.service.ConfigurationService;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
	
	@Autowired
	private ConfigurationDAO configurationDAO;

	@Override
	public Configuration find(String key) {
		Configuration c = null;
		try {
			c = configurationDAO.findById(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	@Override
	public String getString(String key) {
		String result = null;
		try {
			Configuration c = configurationDAO.findById(key);
			result = c.getValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public String getString(String key, String defaultValue) {
		String result = getString(key);
		if (result == null) {
			result = defaultValue;
		}
		return result;
	}
	
	@Override
	public int getInt(String key) {
		int result = -999;
		try {
			Configuration c = configurationDAO.findById(key);
			result = Integer.parseInt(c.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public int getInt(String key, int defaultValue) {
		int result = getInt(key);
		if (result < 0) {
			result = defaultValue;
		}
		return result;
	}

	@Override
	public double getDouble(String key) {
		double result = -999.0;
		try {
			Configuration c = configurationDAO.findById(key);
			result = Double.parseDouble(c.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public double getDouble(String key, double defaultValue) {
		double result = getDouble(key);
		if (result < 0) {
			result = defaultValue;
		}
		return result;
	}
	
	@Override
	public void update(Configuration entity) {
		try {
			configurationDAO.saveOrUpdate(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
