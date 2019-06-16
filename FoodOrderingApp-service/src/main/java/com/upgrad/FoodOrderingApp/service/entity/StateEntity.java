package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * StateEntity class contains all the attributes to be mapped to all the fields in 'state' table in the database
 */
@Entity
@Table(name = "state")
@NamedQueries({
        @NamedQuery(name = "stateByUUID", query = "select c from StateEntity c where c.uuid = :uuid"),
        @NamedQuery(name = "allStates", query = "select q from StateEntity q"),
})
public class StateEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "state_name")
    @NotNull
    @Size(max = 30)
    private String statename;

    public StateEntity() {}

    public StateEntity(String uuid, String name) {
        this.uuid = uuid;
        this.statename = name;
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

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

}