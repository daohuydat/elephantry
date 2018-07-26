package com.elephantry.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.elephantry.dao.AbstractGenericDAO;
import com.elephantry.dao.RoleDAO;
import com.elephantry.entity.Role;

@Repository
@Transactional
public class RoleDAOImpl extends AbstractGenericDAO<Role, String> implements RoleDAO {

}
