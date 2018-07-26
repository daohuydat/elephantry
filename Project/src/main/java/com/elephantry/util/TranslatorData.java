package com.elephantry.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.elephantry.entity.Translation;
import com.elephantry.service.TranslationService;
import com.elephantry.service.impl.TranslationServiceImpl;


public class TranslatorData {
	private static TranslatorData instance;
	
	private Map<String, String> vi_keywords;
	private Map<String, String> en_keywords;
	
	
	public enum Language {
		ENGLISH("en"),
		VIETNAMESE("vi");
		private final String value;
		private Language(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
	}
	
	

	private TranslatorData() {
		ApplicationContext applicationContext = null;
		try {
			vi_keywords = new HashMap<>();
			en_keywords = new HashMap<>();
			
			applicationContext = ContextLoader.getCurrentWebApplicationContext();
			TranslationService translationService = (TranslationServiceImpl) applicationContext.getBean("translationService");
			List<Translation> translations = translationService.listTranslations();
			
			for(Translation t : translations){
				try {
					vi_keywords.put(t.getTranslationKey(), t.getVietnamese());
					en_keywords.put(t.getTranslationKey(), t.getEnglish());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static TranslatorData getInstance() {
		
		if (instance == null) {
			synchronized (TranslatorData.class) {
				if (instance == null) {
					instance = new TranslatorData();
				}
			}
		}
		return instance;
	}
	
	
	public Map<String, String> getTranslatorMap(Language language){
		if (language == Language.ENGLISH) {
			return en_keywords;
		}
		return vi_keywords;
	}
	
}
