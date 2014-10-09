package com.test.mvc.security;

import com.test.mvc.valueObjects.User;

/**
 * Created by rurik on 10/8/14.
 */
public class SuperSecurityService {
    private User logedInUser;

    public void login(User user) {
        if (logedInUser ==null) {
            logedInUser = user;
        }
    }

    public void logout() {
        logedInUser = null;
    }

    public boolean isLogedIn(User user) {
        return logedInUser !=null && logedInUser.equals(user);
    }

    public User getLogedInUser() {
        return logedInUser;
    }
}
