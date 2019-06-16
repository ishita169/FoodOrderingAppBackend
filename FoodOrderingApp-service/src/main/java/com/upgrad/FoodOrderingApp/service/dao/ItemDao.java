package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * ItemDao class provides the database access for all the endpoints in item controller
 */
@Repository
public class ItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Returns item entity for a given UUID
     *
     * @param uuid UUID of item entity
     *
     * @return ItemEntity object
     */
    public ItemEntity getItemByUUID(String uuid) {
        try {
            return entityManager.createNamedQuery("itemByUUID", ItemEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}