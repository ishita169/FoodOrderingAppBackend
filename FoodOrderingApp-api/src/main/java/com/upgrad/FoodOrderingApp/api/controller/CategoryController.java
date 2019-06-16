package com.upgrad.FoodOrderingApp.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upgrad.FoodOrderingApp.api.model.CategoryListResponse;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;

@RestController 
public class CategoryController {
	@Autowired
	private CategoryService categoryservice;
	
		
	@RequestMapping("/category")
    public List<CategoryListResponse> getAllCategory() {
		List<CategoryListResponse> categoryList = new ArrayList<>();
		List<CategoryEntity> categories =categoryservice.getAllCategory();
		
		if(categories!=null && categories.size()>0) {
			for(CategoryEntity categoryEntity : categories) {
				CategoryListResponse category = new CategoryListResponse();
				category.setCategoryName(categoryEntity.getCategoryName());
				category.setId(UUID.fromString(categoryEntity.getUuid()));
				categoryList.add(category);
			}
		}
       return categoryList;
    }

}

