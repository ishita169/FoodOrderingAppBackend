package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * CategoryDao class provides the database access for all the endpoints in category controller
 */
@Repository
public class CategoryDao {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * This method finds all categories
	 *
	 * @return List<CategoryEntity> object
	 */
	public List<CategoryEntity> getAllCategories() {
		try {
			return entityManager.createNamedQuery("allCategories", CategoryEntity.class).getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * This method returns category entity for a given uuid
	 *
	 * @param uuid UUID of category entity
	 *
	 * @return CategoryEntity object
	 */
	public CategoryEntity getCategoryByUuid(String uuid) {
		try {
			return entityManager.createNamedQuery("categoryByUuid", CategoryEntity.class).setParameter("uuid", uuid).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
}