package com.elephantry.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "translation")
@Table(name = "translation")
public class Translation {
	@Id
	@Column(name = "TranslationKey")
	private String translationKey;

	@Column(name = "English")
	private String english;

	@Column(name = "Vietnamese")
	private String vietnamese;

	public Translation() {

	}

	public String getTranslationKey() {
		return translationKey;
	}

	public void setTranslationKey(String translationKey) {
		this.translationKey = translationKey;
	}

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public String getVietnamese() {
		return vietnamese;
	}

	public void setVietnamese(String vietnamese) {
		this.vietnamese = vietnamese;
	}

}
