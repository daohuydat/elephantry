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

@Entity(name = "recommendation")
@Table(name = "recommendation")
public class Recommendation {

	@Id
	@Column(name = "RecommendationId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int recommendationId;

	@Column(name = "Name")
	private String name;

	@Column(name = "FolderInputURL")
	private String folderInputURL;

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public int getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public void setEstimatedDuration(double estimatedDuration) {
		this.estimatedDuration = Math.ceil(estimatedDuration);
	}

	@Column(name = "FolderOutputURL")
	private String folderOutputURL;

	@Column(name = "NumOfItem")
	private int numOfItem;

	@Column(name = "EstimatedDuration")
	private double estimatedDuration; // min

	@Column(name = "RecommendationStatusId")
	private int recommendationStatusId;

	@Column(name = "RecommendTypeId")
	private int recommendTypeId;

	@Column(name = "Priority")
	private int priority;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreatedTime")
	private Date createdTime;

	@Column(name = "StartedTime")
	private Date startedTime;

	@Column(name = "FinishedTime")
	private Date finishedTime;

	@Column(name = "Timer")
	private Date timer;

	@Column(name = "Threshold")
	private double threshold;
	
	@Column(name = "FailedCount")
	private int failedCount;
	
	@Column(name = "RecordCount")
	private int recordCount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CustomerId", nullable = false)
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Recommendation() {

	}

	public int getRecommendationId() {
		return recommendationId;
	}

	public void setRecommendationId(int recommendationId) {
		this.recommendationId = recommendationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartedTime() {
		return startedTime;
	}

	public void setStartedTime(Date startedTime) {
		this.startedTime = startedTime;
	}

	public String getFolderOutputURL() {
		return folderOutputURL;
	}

	public void setFolderOutputURL(String folderOutputURL) {
		this.folderOutputURL = folderOutputURL;
	}

	public String getFolderInputURL() {
		return folderInputURL;
	}

	public void setFolderInputURL(String folderInputURL) {
		this.folderInputURL = folderInputURL;
	}

	public int getNumOfItem() {
		return numOfItem;
	}

	public void setNumOfItem(int numOfItem) {
		this.numOfItem = numOfItem;
	}

	public Double getEstimatedDuration() {
		return estimatedDuration;
	}

	public void setEstimatedDuration(Double estimatedDuration) {
		this.estimatedDuration = estimatedDuration;
	}

	public Date getTimer() {
		return timer;
	}

	public void setTimer(Date timer) {
		this.timer = timer;
	}

	public int getRecommendationStatusId() {
		return recommendationStatusId;
	}

	public void setRecommendationStatusId(int recommendationStatusId) {
		this.recommendationStatusId = recommendationStatusId;
	}

	public int getRecommendTypeId() {
		return recommendTypeId;
	}

	public void setRecommendTypeId(int recommendTypeId) {
		this.recommendTypeId = recommendTypeId;
	}

	public Date getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = finishedTime;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public void start() {
		startedTime = new Date();
	}
	
	public void finish() {
		finishedTime = new Date();
		estimatedDuration = 0;
	}

	public void increaseFailedCount(){
		failedCount++;
	}
}
