package com.elephantry.dao;

import java.util.List;

import com.elephantry.entity.Customer;

public interface CustomerDAO extends GenericDAO<Customer, Integer> {
	
	void updateProfile(Customer customer);
	
	Customer findByEmail(String email);
	
	List<Customer> searchCustomer(String email, String keyword, int pageNum, int itemPerPage) throws Exception;

	int getCustomerCount(String email, String keyword);
}
