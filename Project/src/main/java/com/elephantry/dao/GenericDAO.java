package com.elephantry.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<E, K extends Serializable> {
	/**
	 * 
	 * @param entity:
	 *            entity to save
	 * @return Identifier of saved entity
	 */
	K save(E entity);

	/**
	 * 
	 * @param entity:entity
	 *            to save or update
	 */
	public void saveOrUpdate(E entity);

	
	
	/**
	 * Find all records
	 * 
	 * @return
	 */
	List<E> findAll();

	
	/**
	 * Find by primary key
	 * 
	 * @param id
	 * @return unique entity
	 */
	E findById(K id);

	/**
	 * Clear session
	 */
	void clear();

	/**
	 * Flush session
	 */
	void flush();
}
