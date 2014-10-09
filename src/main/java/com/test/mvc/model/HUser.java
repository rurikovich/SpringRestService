package com.test.mvc.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rurik on 10/7/14.
 */
@Entity
@Table(name = "HUser")
public class HUser implements Serializable {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String login;

    private int money;

    public HUser(String login, int money) {
        this.login = login;
        this.money = money;
    }

    public HUser() {
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_buss", joinColumns = {
            @JoinColumn(name = "USER_ID", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "BUS_ID",
                    nullable = false, updatable = false)})
    private Set<HBus> buss = new HashSet<HBus>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Set<HBus> getBuss() {
        return buss;
    }

    public void setBuss(Set<HBus> buss) {
        this.buss = buss;
    }

    @Override
    public String toString() {
        return "id=" + id + ", login=" + login + ", money=" + money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HUser)) return false;

        HUser hUser = (HUser) o;

        if (id != hUser.id) return false;
        if (money != hUser.money) return false;
        if (!login.equals(hUser.login)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + login.hashCode();
        result = 31 * result + money;
        return result;
    }
}
