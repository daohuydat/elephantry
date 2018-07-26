package com.elephantry.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
/*import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.elephantry.dao.AbstractGenericDAO;
import com.elephantry.dao.CustomerDAO;
import com.elephantry.entity.Customer;
import com.elephantry.entity.User;
import com.elephantry.model.E;
import com.elephantry.service.UserService;

@Repository
@Transactional
public class CustomerDAOImpl extends AbstractGenericDAO<Customer, Integer> implements CustomerDAO {

	@Autowired
	private UserService userService;

	@Override
	public Integer save(Customer entity) {
		Session session = getSession();
		session.save(entity.getUser());
		entity.setCustomerId(entity.getUser().getUserId());
		session.save(entity);
		return entity.getCustomerId();
	}

	@Override
	public void updateProfile(Customer customer) {
		saveOrUpdate(customer);

	}

	@Override
	public Customer findByEmail(String email) {
		User user = userService.findUserByEmail(email);
		return findById(user.getUserId());
	}
	
	@Override
	public int getCustomerCount(String email, String keyword) {
		keyword = keyword.trim();
		email = email.trim();
		
		Query query = null;
		if (keyword.isEmpty() && email.isEmpty()) {
			query = getSession().createSQLQuery("select count(*) from customer c join user u ON c.CustomerId=u.UserId AND u.RoleId = :roleId");
		}else if (!keyword.isEmpty() && email.isEmpty()) {
			query = getSession().createSQLQuery("select count(*) from customer c join user u ON c.CustomerId=u.UserId"
					+ " WHERE MATCH(FirstName, LastName, Website, Company, Phone) AGAINST(:keyword IN NATURAL LANGUAGE MODE) AND u.RoleId = :roleId");
			query.setString("keyword", keyword);
		}else if(keyword.isEmpty() && !email.isEmpty()){
			query = getSession().createSQLQuery("select count(*) from customer c join user u ON c.CustomerId=u.UserId"
					+ " WHERE u.Email like :email AND u.RoleId = :roleId");
			query.setString("email", "%"+email+"%");
		}else{
			query = getSession().createSQLQuery("select count(*) from customer c join user u ON c.CustomerId=u.UserId"
					+ " WHERE MATCH(FirstName, LastName, Website, Company, Phone) AGAINST(:keyword IN NATURAL LANGUAGE MODE)"
					+ " AND u.Email like :email AND u.RoleId = :roleId");
			query.setString("keyword", keyword);
			query.setString("email", "%"+email+"%");
		}
		query.setString("roleId", E.Role.CUSTOMER.getValue());
		Number count = (Number) query.uniqueResult();
		return count == null ? 0 : count.intValue();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> searchCustomer(String email, String keyword, int pageNum, int itemPerPage) throws Exception {
		if (pageNum<1) {
			throw new Exception("pagenum smaller than 1");
		}
		keyword = keyword.trim();
		email = email.trim();
		Query query = null;
		if (keyword.isEmpty() && email.isEmpty()) {
			query = getSession().createSQLQuery("select {c.*}, {u.*} from customer c join user u ON c.CustomerId=u.UserId"
					+ " WHERE u.RoleId = :roleId")
					.addEntity("c",Customer.class)
					.addJoin("u","c.user");
		}else if (!keyword.isEmpty() && email.isEmpty()) {
			query = getSession().createSQLQuery("select {c.*}, {u.*} from customer c join user u ON c.CustomerId=u.UserId"
					+ " WHERE MATCH(FirstName, LastName, Website, Company, Phone) AGAINST(:keyword IN NATURAL LANGUAGE MODE) AND u.RoleId = :roleId")
					.addEntity("c",Customer.class)
					.addJoin("u","c.user");
			query.setString("keyword", keyword);
		}else if(keyword.isEmpty() && !email.isEmpty()){
			query = getSession().createSQLQuery("select {c.*}, {u.*} from customer c join user u ON c.CustomerId=u.UserId"
					+ " WHERE u.Email like :email AND u.RoleId = :roleId")
					.addEntity("c",Customer.class)
					.addJoin("u","c.user");
			query.setString("email", "%"+email+"%");
		}else{
			query = getSession().createSQLQuery("select {c.*}, {u.*} from customer c join user u ON c.CustomerId=u.UserId"
					+ " WHERE MATCH(FirstName, LastName, Website, Company, Phone) AGAINST(:keyword IN NATURAL LANGUAGE MODE)"
					+ " AND u.Email like :email AND u.RoleId = :roleId")
					.addEntity("c",Customer.class)
					.addJoin("u","c.user");
			query.setString("keyword", keyword);
			query.setString("email", "%"+email+"%");
		}
		int start = (pageNum-1)*itemPerPage;
		query.setString("roleId", E.Role.CUSTOMER.getValue());
		query.setFirstResult(start);
		query.setMaxResults(itemPerPage);
		List<Object[]> rows = query.list();
		List<Customer> listCustomer = new ArrayList<>();
		for (Object[] row : rows) {
			Customer c = (Customer) row[0];
			c.setUser((User) row[1]);
			listCustomer.add(c);
		}
		
		return listCustomer;
	}
}
