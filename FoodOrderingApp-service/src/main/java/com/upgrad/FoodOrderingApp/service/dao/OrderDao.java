package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager entityManager;


    public CouponEntity getCouponByName(final String couponName) {
        try {
            return entityManager.createNamedQuery("getCouponByName", CouponEntity.class).setParameter("coupon_name", couponName).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
