package com.elephantry.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "paymentmethod")
@Table(name = "paymentmethod")
public class PaymentMethod {
	
	@Id
	@Column(name = "PaymentMethodId")
	private int paymentMethodId;
	
	@Column(name = "PaymentMethodName")
	private String paymentMethodName;
	
	@Column(name = "Description")
	private String description;

	public PaymentMethod() {
		// TODO Auto-generated constructor stub
	}

	public int getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(int paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	
}
