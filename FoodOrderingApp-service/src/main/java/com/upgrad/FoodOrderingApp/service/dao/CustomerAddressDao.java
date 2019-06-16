package com.upgrad.FoodOrderingApp.service.dao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * CustomerAddressDao class provides the database access for all the required endpoints in customer and address controller
 */
@Repository
public class CustomerAddressDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Creates relation between customer and address entity
     *
     * @param customerAddressEntity Customer and address to relate
     *
     * @return CustomerAddressEntity object
     */
    public CustomerAddressEntity createCustomerAddress(CustomerAddressEntity customerAddressEntity) {
        entityManager.persist(customerAddressEntity);
        return customerAddressEntity;
    }
}
