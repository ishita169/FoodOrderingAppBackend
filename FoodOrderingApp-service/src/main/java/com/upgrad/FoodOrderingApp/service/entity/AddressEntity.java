package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * AddressEntity class contains all the attributes to be mapped to all the fields in 'address' table in the database
 */
@Entity
@Table(name = "address")
@NamedQueries(
        {
                @NamedQuery(name = "allAddresses", query = "select q from AddressEntity q"),
        }
)
public class AddressEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "flat_buil_number")
    @NotNull
    @Size(max = 255)
    private String flatBuilNo;

    @Column(name = "locality")
    @NotNull
    @Size(max = 255)
    private String locality;

    @Column(name = "city")
    @NotNull
    @Size(max = 30)
    private String city;

    @Column(name = "pincode")
    @NotNull
    @Size(max = 30)
    private String pincode;

    @ManyToOne
    @JoinColumn(name = "state_id")
    @NotNull
    private StateEntity state;

    @Column(name = "active")
    @NotNull
    private Integer active;

    @ManyToOne
    @JoinTable(name = "customer_address", joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private CustomerEntity customer;

    public AddressEntity() {}

    public AddressEntity(String uuid, String flatBuilNo, String locality, String city, String pincode, StateEntity stateEntity) {
        this.uuid = uuid;
        this.flatBuilNo = flatBuilNo;
        this.locality = locality;
        this.city = city;
        this.pincode = pincode;
        this.state = stateEntity;
        this.active = 1;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customerEntity) {
        this.customer = customerEntity;
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

    public String getFlatBuilNo() {
        return flatBuilNo;
    }

    public void setFlatBuilNo(String flatNumber) {
        this.flatBuilNo = flatNumber;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public StateEntity getState() {
        return state;
    }

    public void setState(StateEntity state) {
        this.state = state;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}