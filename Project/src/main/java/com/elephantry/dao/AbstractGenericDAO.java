package com.elephantry.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public abstract class AbstractGenericDAO<E, K extends Serializable> implements GenericDAO<E, K> {

	private final Class<E> entityClass;

	public AbstractGenericDAO() {
		this.entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}
	
	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public E findById(final K id) {
		return (E) getSession().get(this.entityClass, id);
	}

	@Override
	public K save(E entity) {
		return (K) getSession().save(entity);
	}

	@Override
	public void saveOrUpdate(E entity) {
		getSession().saveOrUpdate(entity);
	}

	@Override
	public List<E> findAll() {
		return getSession().createCriteria(this.entityClass).list();
	}

	@Override
	public void clear() {
		getSession().clear();

	}

	@Override
	public void flush() {
		getSession().flush();
	}
	 
}