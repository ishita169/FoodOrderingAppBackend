package com.upgrad.FoodOrderingApp.service.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;

@Repository
public class CategoryDao {
    @PersistenceUnit
    private EntityManagerFactory emf;
	
	public List<CategoryEntity> getAllCategory(){
		EntityManager em = emf.createEntityManager();
        TypedQuery<CategoryEntity> query = em.createQuery("from CategoryEntity", CategoryEntity.class);
        return query.getResultList();
	}
	
	public CategoryEntity getCategory(String categoryId){
		CategoryEntity categoryEntity = null;
		EntityManager em = emf.createEntityManager();
		try{
			TypedQuery<CategoryEntity> query = em.createQuery("select uuid,categoryName from CategoryEntity where uuid=:uuid", CategoryEntity.class);
	        query.setParameter("uuid", categoryId);
	        categoryEntity = query.getSingleResult();
		}catch(Exception exception){
			//log error
		}
        return categoryEntity;
	}
}
