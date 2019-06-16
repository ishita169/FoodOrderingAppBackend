package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * RestaurantDao class provides the database access for all the endpoints in restaurant controller
 */
@Repository
public class RestaurantDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * This method helps fetch all restaurants
     *
     * @return List<RestaurantEntity> object
     */
    public List<RestaurantEntity> restaurantsByRating() {
        try {
            return entityManager.createNamedQuery("allRestaurantsByRating", RestaurantEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Returns restaurant entity for a given UUID
     *
     * @param uuid UUID of restaurant entity
     *
     * @return RestaurantEntity object
     */
    public RestaurantEntity getRestaurantByUUID(String uuid) {
        try {
            return entityManager.createNamedQuery("restaurantByUUID", RestaurantEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Updates given restaurant entity
     *
     * @param restaurantEntity Update with given entity
     *
     * @return RestaurantEntity object
     */
    public RestaurantEntity updateRestaurantEntity(RestaurantEntity restaurantEntity) {
        return entityManager.merge(restaurantEntity);
    }
}
