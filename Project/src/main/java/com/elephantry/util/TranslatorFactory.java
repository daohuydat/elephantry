package com.elephantry.util;

import com.elephantry.util.TranslatorData.Language;

public class TranslatorFactory {
	public static Translator getTranslator(Language language){
		
		return  new Translator(TranslatorData.getInstance().getTranslatorMap(language));
	}
}
