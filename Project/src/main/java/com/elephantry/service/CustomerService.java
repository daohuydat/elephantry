package com.elephantry.service;

import java.util.List;

import com.elephantry.entity.Customer;

public interface CustomerService {
	Customer getCustomerById(Integer id);
	Integer save(Customer customer);
	void update(Customer customer);
	Customer findByEmail(String email);
	List<Customer> findAll(); 
	List<Customer> search(String email, String keyword, int pageNum, int itemPerPage);
	int getCustomerCount(String email, String keyword);
}
