package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;

/**
 * AddressDao class provides the database access for all the endpoints in address controller
 */
@Repository
public class AddressDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Creates address entity from given address
     *
     * @param addressEntity Address details
     *
     * @return AddressEntity object
     */
    public AddressEntity createAddress(AddressEntity addressEntity) {
        entityManager.persist(addressEntity);
        return addressEntity;
    }

    /**
     * This method helps to fetch address by id
     *
     * @return AddressEntity object
     */
    public AddressEntity getAddressByUUID(String uuid) {
        try {
            return entityManager.createNamedQuery("addressByUUID", AddressEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Updated given address entity
     *
     * @param addressEntity Address to update
     *
     * @return AddressEntity object
     */
    public AddressEntity updateAddressEntity(AddressEntity addressEntity) {
        return entityManager.merge(addressEntity);
    }

    /**
     * Deletes given address entity
     *
     * @param addressEntity Address to delete
     *
     * @return AddressEntity object
     */
    public AddressEntity deleteAddressEntity(AddressEntity addressEntity) {
        entityManager.remove(addressEntity);
        return addressEntity;
    }
}