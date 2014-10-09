package com.test.mvc.service;

/**
 * Created by rurik on 10/7/14.
 */

import com.test.mvc.dao.UserDAO;
import com.test.mvc.model.HUser;
import com.test.mvc.valueObjects.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public User getUserById(int id) {
        return new User(this.userDAO.getUserById(id));
    }

    @Override
    public User getUserByLogin(String login) {
        HUser userByLogin = this.userDAO.getUserByLogin(login);
        return userByLogin != null ? new User(userByLogin) : null;
    }


}