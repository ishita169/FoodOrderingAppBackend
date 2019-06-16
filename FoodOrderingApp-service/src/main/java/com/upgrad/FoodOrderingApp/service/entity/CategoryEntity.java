package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * CategoryEntity class contains all the attributes to be mapped to all the fields in 'category' table in the database
 */
@Entity
@Table(name = "category")
@NamedQueries({
		@NamedQuery(name = "allCategories", query = "select q from CategoryEntity q"),
		@NamedQuery(name = "categoryByUuid", query = "select q from CategoryEntity q where q.uuid = :uuid"),
})
public class CategoryEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "uuid")
	@NotNull
	@Size(max = 200)
	private String uuid;

	@Column(name = "category_name")
	@NotNull
	@Size(max = 255)
	private String categoryName;

	@ManyToMany
	@JoinTable(name = "category_item", joinColumns = @JoinColumn(name = "category_id"),
			inverseJoinColumns = @JoinColumn(name = "item_id"))
	private List<ItemEntity> items = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "restaurant_category", joinColumns = @JoinColumn(name = "category_id"),
			inverseJoinColumns = @JoinColumn(name = "restaurant_id"))
	private List<RestaurantEntity> restaurants = new ArrayList<>();

	public List<RestaurantEntity> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(List<RestaurantEntity> restaurants) {
		this.restaurants = restaurants;
	}

	public List<ItemEntity> getItems() {
		return items;
	}

	public void setItems(List<ItemEntity> items) {
		this.items = items;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}