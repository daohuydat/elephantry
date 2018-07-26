package com.elephantry.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.elephantry.dao.AbstractGenericDAO;
import com.elephantry.dao.FeedbackDAO;
import com.elephantry.entity.Customer;
import com.elephantry.entity.Feedback;

@Repository
@Transactional
public class FeedbackDAOImpl extends AbstractGenericDAO<Feedback, Integer> implements FeedbackDAO{

	@Override
	public List<Feedback> findByCreatedTime(String sortDesc, int pageNum, int itemPerPage, int read) {
		int start = (pageNum-1)*itemPerPage;
		if (sortDesc.equals("1")) {
			sortDesc = "DESC";
		}else{
			sortDesc = "ASC";
		}
		Query query = getSession().createSQLQuery("SELECT {f.*}, {c.*} FROM feedback f join customer c"
				+ " WHERE c.CustomerId = f.CustomerId AND f.Read = :read ORDER BY f.CreatedTime "+sortDesc)
				.addEntity("f", Feedback.class)
				.addJoin("c", "f.customer");
		query.setInteger("read", read);
		query.setFirstResult(start);
		query.setMaxResults(itemPerPage);
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.list();
		List<Feedback> listFeedback = new ArrayList<>();
		for (Object[] row : rows) {
			Feedback f = (Feedback) row[0];
			f.setCustomer((Customer) row[1]);
		listFeedback.add(f);
		}
		return listFeedback;
	}
	@Override
	public int countByStatus(Integer read) {
		
		Query query = getSession().createSQLQuery("SELECT count(*) FROM feedback WHERE `Read` = :read");
		query.setInteger("read", read);
		Number count = (Number) query.uniqueResult();
		return count == null ? 0 : count.intValue();
	}

}
