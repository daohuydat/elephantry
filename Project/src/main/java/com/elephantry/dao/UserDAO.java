package com.elephantry.dao;

import java.util.List;
import java.util.Map;

import com.elephantry.entity.User;

public interface UserDAO extends GenericDAO<User, Integer> {
	
	User findUserByEmail(String email);

	int getAdminCount(String email);

	List<User> searchAdmin(String email, int pageNum, int itemPerPage) throws Exception;
	
	int countCustomerByCreatedTime(int mountAgo);
	
	Map<String, Integer> summaryCustomerByMonth(int monthAgo);
	
	int countCustomerLastWeek();
}
