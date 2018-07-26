package com.elephantry.service;

import com.elephantry.entity.Configuration;

public interface ConfigurationService {

	Configuration find(String key);
	String getString(String key);
	int getInt(String key);
	double getDouble(String key);
	String getString(String key, String defaultValue);
	int getInt(String key, int defaultValue);
	double getDouble(String key, double defaultValue);
	void update(Configuration entity);
}
