package com.elephantry.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "customer")
@Table(name = "customer")
public class Customer {

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "CustomerId", referencedColumnName = "UserId")
	private User user;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "PackageId", referencedColumnName = "PackageId")
	private Package package1;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ProvinceId", referencedColumnName = "ProvinceId")
	private Province province;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	@Id
	@Column(name = "CustomerId")
	private int customerId;

	@Column(name = "FirstName")
	private String firstName;

	@Column(name = "LastName")
	private String lastName;

	@Column(name = "Gender")
	private String gender;

	@Column(name = "DoB")
	private Date doB;

	@Column(name = "Website")
	private String website;

	@Column(name = "Company")
	private String company;

	@Column(name = "Phone")
	private String phone;

	@Column(name = "Address")
	private String address;
	
	@Column(name = "ExpiredCredit")
	private Date expiredCredit;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getExpiredCredit() {
		return expiredCredit;
	}

	public void setExpiredCredit(Date expiredCredit) {
		this.expiredCredit = expiredCredit;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	private List<Recommendation> recommendations;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	private List<UploadedFile> uploadedFiles;

	public Customer() {

	}

	public Customer(int id) {
		this.customerId = id;
	}

	public List<Recommendation> getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(List<Recommendation> recommendations) {
		this.recommendations = recommendations;
	}

	public List<UploadedFile> getUploadedFiles() {
		return uploadedFiles;
	}

	public void setUploadedFiles(List<UploadedFile> uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDoB() {
		return doB;
	}

	public void setDoB(Date doB) {
		this.doB = doB;
	}

	public String getWebsite() {
		return website;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	public Package getPackage1() {
		return package1;
	}

	public void setPackage1(Package package1) {
		this.package1 = package1;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

}
