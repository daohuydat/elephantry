package com.elephantry.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "uploadedfile")
@Table(name = "uploadedfile")
public class UploadedFile {
	@Id
	@Column(name = "UploadedFileId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uploadedFileId;

	@Column(name = "FileName")
	private String fileName;

	@Column(name = "FileSize")
	private double fileSize;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UploadedTime")
	private Date uploadedTime;

	@Column(name = "HDFSURL")
	private String HDFSURL;

	@Column(name = "DeletedTime")
	private Date deletedTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CustomerId", nullable = false)
	private Customer customer;

	public UploadedFile() {

	}

	public int getUploadedFileId() {
		return uploadedFileId;
	}

	public void setUploadedFileId(int uploadedFileId) {
		this.uploadedFileId = uploadedFileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public double getFileSize() {
		return fileSize;
	}

	public void setFileSize(double fileSize) {
		this.fileSize = fileSize / 1048576; // file size in MB
	}

	public Date getUploadedTime() {
		return uploadedTime;
	}

	public void setUploadedTime(Date uploadedTime) {
		this.uploadedTime = uploadedTime;
	}

	public String getHDFSURL() {
		return HDFSURL;
	}

	public void setHDFSURL(String hDFSURL) {
		HDFSURL = hDFSURL;
	}

	public Date getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(Date deletedTime) {
		this.deletedTime = deletedTime;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
