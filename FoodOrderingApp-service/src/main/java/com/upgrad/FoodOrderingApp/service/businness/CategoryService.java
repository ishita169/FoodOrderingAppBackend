package com.upgrad.FoodOrderingApp.service.businness;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;

@Service
public class CategoryService {
	@Autowired
	private CategoryDao categoryDao;
	
	public List<CategoryEntity> getAllCategory(){
		return categoryDao.getAllCategory();
	}
	
	public CategoryEntity getCategory(String categoryId){
		return categoryDao.getCategory(categoryId);
	}
}
