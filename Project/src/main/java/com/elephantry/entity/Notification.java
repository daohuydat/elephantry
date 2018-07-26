package com.elephantry.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "notification")
@Table(name = "notification")
public class Notification {
	@Id
	@Column(name = "NotiId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int notiId;

	@Column(name = "`Read`")
	private int read;

	@Column(name = "Message")
	private String message;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreatedTime")
	private Date createdTime;

	@Column(name = "UserId")
	private int userId;

	@Column(name = "NotiTypeId")
	private int notiTypeId;

	public Notification() {
		this.read = 0;
	}

	public int getNotiId() {
		return notiId;
	}

	public void setNotiId(int notiId) {
		this.notiId = notiId;
	}

	public int getRead() {
		return read;
	}

	public void setRead(int read) {
		this.read = read;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getNotiTypeId() {
		return notiTypeId;
	}

	public void setNotiTypeId(int notiTypeId) {
		this.notiTypeId = notiTypeId;
	}

}
