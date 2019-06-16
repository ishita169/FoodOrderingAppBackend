package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * CouponDao class provides the database access for the required endpoints in order controller
 */
@Repository
public class CouponDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * This method helps find coupon details by coupon name
     *
     * @param couponName Name of the coupon to get the details for
     *
     * @return CouponEntity object
     */
    public CouponEntity getCouponByCouponName(String couponName) {
        try {
            return entityManager.createNamedQuery("couponByCouponName", CouponEntity.class).setParameter("couponName", couponName).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Returns coupon entity for a given UUID
     *
     * @param uuid UUID of coupon entity
     *
     * @return CouponEntity object
     */
    public CouponEntity getCouponByCouponUUID(String uuid) {
        try {
            return entityManager.createNamedQuery("couponByUUID", CouponEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}