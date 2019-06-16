package com.upgrad.FoodOrderingApp.service.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * RestaurantEntity class contains all the attributes to be mapped to all the fields in 'restaurant' table in the database
 */
@Entity
@Table(name = "restaurant")
@NamedQueries(
        {
                @NamedQuery(name = "allRestaurantsByRating", query = "select q from RestaurantEntity q order by q.customerRating desc"),
                @NamedQuery(name = "restaurantByUUID", query = "select q from RestaurantEntity q where q.uuid = :uuid"),
        }
)
public class RestaurantEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "restaurant_name")
    @NotNull
    @Size(max = 50)
    private String restaurantName;

    @Column(name = "photo_url")
    @NotNull
    @Size(max = 255)
    private String photoUrl;

    @Column(name = "customer_rating")
    @NotNull
    private BigDecimal customerRating;

    @Column(name = "average_price_for_two")
    @NotNull
    private Integer avgPriceForTwo;

    @Column(name = "number_of_customers_rated")
    @NotNull
    private Integer customersRated;

    @ManyToOne
    @JoinColumn(name = "address_id")
    @NotNull
    private AddressEntity address;

    @ManyToMany
    @JoinTable(name = "restaurant_category", joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<CategoryEntity> categories = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "restaurant_item", joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<ItemEntity> items = new ArrayList<>();

    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
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

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Double getCustomerRating() {
        return customerRating.doubleValue();
    }

    public void setCustomerRating(Double customerRating) {
        this.customerRating = new BigDecimal(customerRating);
    }

    public Integer getAvgPrice() {
        return avgPriceForTwo;
    }

    public void setAvgPrice(Integer avgPriceForTwo) {
        this.avgPriceForTwo = avgPriceForTwo;
    }

    public Integer getNumberCustomersRated() {
        return customersRated;
    }

    public void setNumberCustomersRated(Integer customersRated) {
        this.customersRated = customersRated;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }
}