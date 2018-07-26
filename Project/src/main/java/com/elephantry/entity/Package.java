package com.elephantry.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "package")
@Table(name = "package")
public class Package {
	
	@Id
	@Column(name = "PackageId")
	private int packageId;
	
	@Column(name = "PackageName")
	private String packageName;
	
	@Column(name = "Price")
	private Double price;
	
	@Column(name = "Storage")
	private Double storage;
	
	@Column(name = "Priority")
	private int priority;

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getStorage() {
		return storage;
	}

	public void setStorage(Double storage) {
		this.storage = storage;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Package(int packageId, String packageName, Double price, Double storage, int priority) {
		super();
		this.packageId = packageId;
		this.packageName = packageName;
		this.price = price;
		this.storage = storage;
		this.priority = priority;
	}

	public Package() {
		super();
	}
	
	
}
