package com.elephantry.service;

import java.util.List;

import com.elephantry.entity.Role;

public interface RoleService {
	List<Role> findAll();
	Role findById(String id);
}
