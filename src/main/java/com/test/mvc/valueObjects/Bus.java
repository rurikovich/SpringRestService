package com.test.mvc.valueObjects;

import com.test.mvc.model.HBus;
import com.test.mvc.model.HUser;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rurik on 10/8/14.
 */
public class Bus {

    private int id;
    private String name;
    private int cost;
    @JsonIgnore
    private Set<HUser> hUsers = new HashSet<HUser>();

    public Bus(HBus hBus) {
        this.id = hBus.getId();
        this.name = hBus.getName();
        this.cost = hBus.getCost();
        this.hUsers = hBus.gethUsers();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public Set<HUser> gethUsers() {
        return hUsers;
    }
}
