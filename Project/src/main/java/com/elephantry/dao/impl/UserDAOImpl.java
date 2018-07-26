package com.elephantry.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.elephantry.dao.AbstractGenericDAO;
import com.elephantry.dao.UserDAO;
import com.elephantry.entity.User;
import com.elephantry.model.E;

@Repository
@Transactional
public class UserDAOImpl extends AbstractGenericDAO<User, Integer> implements UserDAO {

	@SuppressWarnings("unchecked")
	@Override
	public User findUserByEmail(String email) {
		Query query = getSession().createQuery("from user where Email = :email");
		query.setString("email", email);
		List<User> list = query.list();
		if (list != null && list.size() > 0) {
			return  list.get(0);
		}
		return null;
	}

	@Override
	public int getAdminCount(String email) {
		email = email.trim();
		Query query = getSession().createSQLQuery("select count(*) from user where RoleId= :roleId and Email like :q");
		query.setString("roleId", E.Role.ADMIN.getValue());
		query.setString("q", "%" + email + "%");
		Number count = (Number) query.uniqueResult();
		return count == null ? 0 : count.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> searchAdmin(String email, int pageNum, int itemPerPage) throws Exception {
		if (pageNum<1) {
			throw new Exception("pagenum smaller than 1");
		}
		email = email.trim();
		Query query = null;
		if(email.isEmpty()){
			query = getSession().createQuery("from user where RoleId = :roleId");
		}else{
			query = getSession().createQuery("from user where RoleId= :roleId and Email like :q");
			query.setString("q", "%" + email + "%");
		}
		int start = (pageNum-1)*itemPerPage;
		query.setFirstResult(start);
		query.setMaxResults(itemPerPage);
		query.setString("roleId", E.Role.ADMIN.getValue());
		return query.list();
	}

	@Override
	public int countCustomerByCreatedTime(int mountAgo) {
		Query query = getSession().createSQLQuery("select count(UserId) from user "
				+ "where CreatedTime <= DATE_SUB(NOW(), INTERVAL :monthAgo month) and RoleId = :role");
		query.setInteger("monthAgo", mountAgo);
		query.setString("role", E.Role.CUSTOMER.getValue());
		Number result = (Number) query.uniqueResult();
		return result==null?0:result.intValue();
	}

	@Override
	public Map<String, Integer> summaryCustomerByMonth(int monthAgo) {
		Query query = getSession().createSQLQuery("SELECT year(CreatedTime), month(CreatedTime), COUNT(UserId)"
				+" FROM user WHERE RoleId = :role AND CreatedTime > DATE_SUB(NOW(), INTERVAL :monthAgo month)"
				+" GROUP BY month(CreatedTime), year(CreatedTime)");
		query.setInteger("monthAgo", monthAgo+1);
		query.setString("role", E.Role.CUSTOMER.getValue());
		Map<String, Integer> map = new HashMap<>();
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.list();
		for (Object[] row : rows) {
			map.put(row[0]+"-"+row[1], Integer.parseInt(row[2].toString()));
		}
		return map;
	}

	@Override
	public int countCustomerLastWeek() {
		Query query = getSession().createSQLQuery("select count(*) from user where CreatedTime <= DATE_SUB(NOW(), INTERVAL 1 week) and RoleId = :role");
		query.setString("role", E.Role.CUSTOMER.getValue());
		Number result = (Number) query.uniqueResult();
		return result==null?0:result.intValue();
	}

}
