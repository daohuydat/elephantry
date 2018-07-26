package com.elephantry.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elephantry.dao.UserDAO;
import com.elephantry.entity.User;
import com.elephantry.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public List<User> findAll() {
		
		return userDAO.findAll();
	}

	@Transactional
	@Override
	public Integer save(User entity) {
		
		return userDAO.save(entity);
	}

	@Override
	public void checkEmail(String email) {
		
		
	}

	@Override
	public User findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userDAO.findUserByEmail(email);
	}

	@Override
	public User findByEmail(String email) {
		User user = null;
		try {
			user = userDAO.findUserByEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public int getAdminCount(String email) {
		int k = 0;
		try {
			k = userDAO.getAdminCount(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return k;
	}

	@Override
	public List<User> searchAdmin(String name, int pageNum, int itemPerPage) {
		List<User> user = null;
		try {
			user = userDAO.searchAdmin(name,pageNum,itemPerPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void update(User entity) {
		try {
			userDAO.saveOrUpdate(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public User findById(Integer userId) {
		User user = null;
		try {
			user = userDAO.findById(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Map<String, Integer> summaryCustomerByMonth(Integer monthAgo) {
		try {
			return userDAO.summaryCustomerByMonth(monthAgo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int countCustomerByCreatedTime(Integer monthAgo) {
		try {
			return userDAO.countCustomerByCreatedTime(monthAgo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countTotalCustomerLastWeek() {
		try {
			return userDAO.countCustomerLastWeek();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	

}
