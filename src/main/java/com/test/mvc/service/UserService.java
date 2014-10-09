package com.test.mvc.service;

import com.test.mvc.valueObjects.User;

/**
 * Created by rurik on 10/7/14.
 */

public interface UserService {

    public User getUserById(int id);
    public User getUserByLogin(String login);

}
