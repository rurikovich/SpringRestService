package com.test.mvc.model;

/**
 * Created by rurik on 10/7/14.
 */


import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "HBUS")
public class HBus {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;

    private int  cost;

    public HBus(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public HBus() {
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,mappedBy="buss")
    private Set<HUser> hUsers= new HashSet<HUser>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Set<HUser> gethUsers() {
        return hUsers;
    }

    public void sethUsers(Set<HUser> hUsers) {
        this.hUsers = hUsers;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name + ", cost=" + cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HBus)) return false;

        HBus hBus = (HBus) o;

        if (cost != hBus.cost) return false;
        if (id != hBus.id) return false;
        if (!name.equals(hBus.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + cost;
        return result;
    }
}

