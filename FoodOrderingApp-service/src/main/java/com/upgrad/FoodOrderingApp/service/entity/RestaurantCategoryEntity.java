package com.upgrad.FoodOrderingApp.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * Description - Entity class for the restaurant_category table
 */
@Entity
@Table(name = "restaurant_category")
public class RestaurantCategoryEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;


	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "restaurant_id", referencedColumnName = "id")
	private RestaurantEntity restaurant;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private CategoryEntity category;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public RestaurantEntity getRestaurantEntity() {
		return restaurant;
	}

	public void setRestaurantEntity(RestaurantEntity restaurant) {
		this.restaurant = restaurant;
	}

	public CategoryEntity getCategoryEntity() {
		return category;
	}

	public void setCategoryEntity(CategoryEntity category) {
		this.category = category;
	}

	@Override
	public boolean equals(Object obj) {
		return new EqualsBuilder().append(this, obj).isEquals();
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
