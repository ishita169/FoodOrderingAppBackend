package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * OrderItemDao class provides the database access for all the required endpoints in order and item controller
 */
@Repository
public class OrderItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Creates relation between order and item entity
     *
     * @param orderItemEntity Order and item to relate
     *
     * @return OrderItemEntity object
     */
    public OrderItemEntity createOrderItemEntity(OrderItemEntity orderItemEntity) {
        entityManager.persist(orderItemEntity);
        return orderItemEntity;
    }

    /**
     * Returns items for a given order entity
     *
     * @param orderEntity Order to get items for
     *
     * @return List<OrderItemEntity> object
     */
    public List<OrderItemEntity> getItemsByOrder(OrderEntity orderEntity) {
        try {
            return entityManager.createNamedQuery("itemsByOrder", OrderItemEntity.class).setParameter("orderEntity", orderEntity).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
}