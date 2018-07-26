package com.elephantry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elephantry.dao.RoleDAO;
import com.elephantry.entity.Role;
import com.elephantry.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDAO roleDAO;
	
	@Override
	public List<Role> findAll() {
		return roleDAO.findAll();
	}

	@Override
	public Role findById(String id) {
		return roleDAO.findById(id);
	}

}
