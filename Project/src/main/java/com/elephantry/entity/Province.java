package com.elephantry.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "province")
@Table(name = "province")
public class Province {
	@Id
	@Column(name = "ProvinceId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int provinceId;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "CountryId", referencedColumnName = "CountryId")
	private Country country;
	
	@Column(name = "ProvinceName")
	private String provinceName;
	

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Province(int provinceId, String provinceName, String countryId) {
		super();
		this.provinceId = provinceId;
		this.provinceName = provinceName;
	}

	public Province() {
		super();
	}
	
	public Country getCountry(){
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	
}
