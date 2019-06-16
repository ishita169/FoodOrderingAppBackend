package com.upgrad.FoodOrderingApp.service.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;

@Repository
public class PaymentDao {
	
	@PersistenceContext
	private EntityManager entitymanager;
	
	/**
	 * This method finds all PaymentMethod
	 *
	 * @return List<PaymentEntity> object
	 */
	public List<PaymentEntity> getAllPaymentMethod() {
		try {
		return entitymanager.createNamedQuery("allPaymentMethods", PaymentEntity.class).getResultList();	
			
		} catch (NoResultException nre) {
			return null;
		}
	}

}
