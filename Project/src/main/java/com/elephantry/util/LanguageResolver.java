package com.elephantry.util;

import java.util.Optional;

import org.springframework.ui.Model;

import com.elephantry.util.TranslatorData.Language;

public class LanguageResolver {
	
	public static Language getPageLanguage(Optional<String> lang){
		if (lang.isPresent()) {
			System.out.println("lang: " + lang);
			if (Language.ENGLISH.getValue().equalsIgnoreCase(lang.get())) {
				return Language.ENGLISH;
			} else if (Language.VIETNAMESE.getValue().equalsIgnoreCase(lang.get())) {
				return Language.VIETNAMESE;
			}
		}
		return Language.ENGLISH;
	}
	
	public static String resolve(Optional<String> lang, Model model){
		Language  language = getPageLanguage(lang);
		model.addAttribute("translator", TranslatorFactory.getTranslator(language));
		model.addAttribute("language", language.getValue());
		return language.getValue();
	}
}
