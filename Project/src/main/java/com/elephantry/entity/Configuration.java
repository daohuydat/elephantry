package com.elephantry.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "configuration")
@Table(name = "configuration")
public class Configuration {

	@Id
	@Column(name = "`Key`")
	private String key;

	@Column(name = "`Value`")
	private String value;

	public Configuration() {

	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
