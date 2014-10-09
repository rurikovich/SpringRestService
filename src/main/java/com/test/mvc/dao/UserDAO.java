package com.test.mvc.dao;

/**
 * Created by rurik on 10/7/14.
 */

import com.test.mvc.model.HUser;


public interface UserDAO {
    public HUser getUserById(int id);
    public HUser getUserByLogin(String login);
    public void save(HUser user);
    public void addUser(String login,int money);
    public void buyBus(int userId, int busId);
    public void sellBus(int userId, int busId);

}
