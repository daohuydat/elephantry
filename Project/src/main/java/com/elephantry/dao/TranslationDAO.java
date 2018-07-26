package com.elephantry.dao;

import java.util.List;

import com.elephantry.entity.Translation;


public interface TranslationDAO extends GenericDAO<Translation, String>{
	List<Translation> listTranslations();
}
