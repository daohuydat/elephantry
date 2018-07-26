package com.elephantry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elephantry.dao.CustomerDAO;
import com.elephantry.entity.Customer;
import com.elephantry.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDAO customerDAO;

	@Override
	public Customer getCustomerById(Integer id) {
		try {
			return customerDAO.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer save(Customer customer) {
		Integer result = 0;
		try {
			result = customerDAO.save(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void update(Customer customer) {
		try {
			customerDAO.saveOrUpdate(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Customer findByEmail(String email) {
		
		Customer customer = null ;
		try {
			customer = customerDAO.findByEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public List<Customer> findAll() {
		List<Customer> listCustomer = null;
		try {
			listCustomer = customerDAO.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listCustomer;
	}


	@Override
	public List<Customer> search(String email, String keyword, int pageNum, int itemPerPage) {
		List<Customer> listCustomer = null;
		try {
			listCustomer = customerDAO.searchCustomer(email, keyword, pageNum, itemPerPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listCustomer;
	}

	@Override
	public int getCustomerCount(String email, String keyword) {
		int customerCount = 0;
		try {
			customerCount = customerDAO.getCustomerCount(email, keyword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerCount;
	}
	
	
}
