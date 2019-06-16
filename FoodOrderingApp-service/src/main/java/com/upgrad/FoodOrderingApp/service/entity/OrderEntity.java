package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * OrderEntity class contains all the attributes to be mapped to all the fields in 'orders' table in the database
 */
@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = "ordersByAddress", query = "select q from OrderEntity q where q.address = :address"),
        @NamedQuery(name = "ordersByCustomer", query = "select q from OrderEntity q where q.customer = :customer order by q.date desc "),
        @NamedQuery(name = "ordersByRestaurant", query = "select q from OrderEntity q where q.restaurant = :restaurant"),
})
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "bill")
    @NotNull
    private BigDecimal bill;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    @NotNull
    private CouponEntity coupon;

    @Column(name = "discount")
    @NotNull
    private BigDecimal discount;

    @Column(name = "date")
    @NotNull
    private Date date;

    @OneToOne
    @JoinColumn(name = "payment_id")
    @NotNull
    private PaymentEntity payment;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @NotNull
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "address_id")
    @NotNull
    private AddressEntity address;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @NotNull
    private RestaurantEntity restaurant;

    public OrderEntity() {}

    public OrderEntity(@NotNull @Size(max = 200) String uuid, @NotNull Double bill, @NotNull CouponEntity coupon, @NotNull Double discount, @NotNull Date date, @NotNull PaymentEntity payment, @NotNull CustomerEntity customer, @NotNull AddressEntity address, RestaurantEntity restaurant) {
        this.uuid = uuid;
        this.bill = new BigDecimal(bill);
        this.coupon = coupon;
        this.discount = new BigDecimal(discount);
        this.date = date;
        this.payment = payment;
        this.customer = customer;
        this.address = address;
        this.restaurant = restaurant;
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

    public Double getBill() {
        return bill.doubleValue();
    }

    public void setBill(Double bill) {
        this.bill = new BigDecimal(bill);
    }

    public CouponEntity getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponEntity coupon) {
        this.coupon = coupon;
    }

    public Double getDiscount() {
        return discount.doubleValue();
    }

    public void setDiscount(Double discount) {
        this.discount = new BigDecimal(discount);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public RestaurantEntity getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
    }
}