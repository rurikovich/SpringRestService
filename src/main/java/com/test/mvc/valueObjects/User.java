package com.test.mvc.valueObjects;

import com.test.mvc.model.HBus;
import com.test.mvc.model.HUser;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rurik on 10/8/14.
 */
public class User {
    private int id;
    private String login;
    private int money;
    private Set<HBus> buss=new HashSet<HBus>();

    public User(HUser user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.money = user.getMoney();
        this.buss.addAll(user.getBuss());
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public int getMoney() {
        return money;
    }

    public Set<HBus> getBuss() {
        return buss;
    }
}
