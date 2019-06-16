package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * OrderDao class provides the database access for all the endpoints in order controller
 */
@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Returns orders for a given address
     *
     * @param addressEntity Address to get orders for
     *
     * @return List<OrderEntity> object
     */
    public List<OrderEntity> getOrdersByAddress(AddressEntity addressEntity) {
        try {
            return entityManager.createNamedQuery("ordersByAddress", OrderEntity.class).setParameter("address", addressEntity).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Crates new order
     *
     * @param orderEntity Order details
     *
     * @return OrderEntity object
     */
    public OrderEntity createOrder(OrderEntity orderEntity) {
        entityManager.persist(orderEntity);
        return orderEntity;
    }

    /**
     * Returns orders for a given customer
     *
     * @param customerEntity Customer to get orders for
     *
     * @return List<OrderEntity> object
     */
    public List<OrderEntity> getOrdersByCustomers(CustomerEntity customerEntity) {
        try {
            return entityManager.createNamedQuery("ordersByCustomer", OrderEntity.class).setParameter("customer", customerEntity).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Returns orders for a given restaurant
     *
     * @param restaurantEntity Restaurant to get orders for
     *
     * @return List<OrderEntity> object
     */
    public List<OrderEntity> getOrdersByRestaurant(RestaurantEntity restaurantEntity) {
        try {
            return entityManager.createNamedQuery("ordersByRestaurant", OrderEntity.class).setParameter("restaurant", restaurantEntity).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Gets the coupon details for a particular coupon name
     * @param couponName Name of coupon to searched
     * @return CouponEntity object
     */
    public CouponEntity getCouponByName(final String couponName) {
        try {
            return entityManager.createNamedQuery("couponByCouponName", CouponEntity.class).setParameter("coupon_name", couponName).getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
    }

}