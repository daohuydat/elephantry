package com.elephantry.entity;

import java.util.Date;

import javax.persistence.CascadeType;
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

@Entity(name = "paymenthistory")
@Table(name = "paymenthistory")
public class PaymentHistory {
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "PaymentMethodId", referencedColumnName = "PaymentMethodId")
	private PaymentMethod paymentMethod;
	
	@Id
	@Column(name = "PaymentHistoryId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int paymentHistoryId;
	
	@Column(name = "Amount")
	private Double amount;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreatedTime")
	private Date createdTime;
	
	@Column(name = "CustomerId")
	private int customerId;
	
	@Column(name = "TransactionId")
	private String transactionId;
	
	public PaymentHistory() {
	}

	public int getPaymentHistoryId() {
		return paymentHistoryId;
	}

	public void setPaymentHistoryId(int paymentHistoryId) {
		this.paymentHistoryId = paymentHistoryId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	
	
	
	
}
