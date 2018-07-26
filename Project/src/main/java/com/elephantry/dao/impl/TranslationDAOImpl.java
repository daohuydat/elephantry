package com.elephantry.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.elephantry.dao.AbstractGenericDAO;
import com.elephantry.dao.TranslationDAO;
import com.elephantry.entity.Translation;

@Repository
@Transactional
public class TranslationDAOImpl extends AbstractGenericDAO<Translation, String> implements TranslationDAO {

	@Override
	public List<Translation> listTranslations() {
		
		return findAll();
	}
	
	

}

