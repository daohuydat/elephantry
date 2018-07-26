package com.elephantry.util;

import java.util.Map;

public class Translator {
	
	private Map<String, String> keywords;
	
	public Translator(Map<String, String> keywords) {
		this.keywords = keywords;
	}
	
	public String get(String key){
		String value = "";
		try {
			value = keywords.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value == null ? key : value;
	}
}
