package com.elephantry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elephantry.dao.TranslationDAO;
import com.elephantry.entity.Translation;
import com.elephantry.service.TranslationService;

@Service
public class TranslationServiceImpl implements TranslationService {

	@Autowired
	private TranslationDAO translationDAO;
	
	@Override
	public List<Translation> listTranslations() {
		
		return translationDAO.listTranslations();
	}

}
