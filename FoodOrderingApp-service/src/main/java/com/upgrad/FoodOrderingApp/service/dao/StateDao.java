package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * StateDao class provides the database access for all the required endpoints in address controller
 */
@Repository
public class StateDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * This method helps fetch existing State by StateID
     *
     * @param uuid the state UUID which will be searched in database to find existing state
     *
     * @return StateEntity object if given state exists in database
     */
    public StateEntity getStateByUUID(String uuid) {
        try {
            return entityManager.createNamedQuery("stateByUUID", StateEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * This method helps to fetch all states
     *
     * @return List<StateEntity> object
     */
    public List<StateEntity> getAllStates() {
        try {
            return entityManager.createNamedQuery("allStates", StateEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
