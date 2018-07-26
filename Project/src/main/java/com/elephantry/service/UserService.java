package com.elephantry.service;

import java.util.List;
import java.util.Map;

import com.elephantry.entity.User;

public interface UserService {
	List<User> findAll();
	
	public Integer save(User entity) ;
	
	void checkEmail(String email);
	
	User findUserByEmail(String email);

	User findByEmail(String email);
	
	User findById(Integer userId);
	
	int getAdminCount(String email);
	
	List<User> searchAdmin(String email, int pageNum, int itemPerPage);
	
	public void update(User entity);
	
	Map<String, Integer> summaryCustomerByMonth(Integer monthAgo);
	
	int countCustomerByCreatedTime(Integer monthAgo);
	
	int countTotalCustomerLastWeek();
}
