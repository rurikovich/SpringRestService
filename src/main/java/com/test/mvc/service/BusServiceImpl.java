package com.test.mvc.service;

/**
 * Created by rurik on 10/7/14.
 */

import com.test.mvc.security.SuperSecurityService;
import com.test.mvc.exceptions.NoSuchBusException;
import com.test.mvc.exceptions.NotEnoughMoneyException;
import com.test.mvc.dao.BusDAO;
import com.test.mvc.dao.UserDAO;
import com.test.mvc.model.HBus;
import com.test.mvc.model.HUser;
import com.test.mvc.valueObjects.Bus;
import com.test.mvc.valueObjects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class BusServiceImpl implements BusService {

    private BusDAO busDAO;
    private UserDAO userDAO;
    private SuperSecurityService securityService;

    @Autowired(required = true)
    @Qualifier(value = "securityService")
    public void setSecurityService(SuperSecurityService securityService) {
        this.securityService = securityService;
    }


    public void setBusDAO(BusDAO busDAO) {
        this.busDAO = busDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public List<Bus> listBuss() {
        return this.busDAO.list();
    }

    @Override
    public void buyBus(User user, String busName) throws NotEnoughMoneyException {
        HBus hBus = busDAO.getBusByName(busName);
        if (user.getMoney() >= hBus.getCost()) {
            userDAO.buyBus(user.getId(), hBus.getId());
            refreshUser();
        } else {
            throw new NotEnoughMoneyException();
        }

    }

    @Override
    public void sellBus(User user, String busName) throws NoSuchBusException {
        HBus hBus = busDAO.getBusByName(busName);
        if (user.getBuss().contains(hBus)) {
            userDAO.sellBus(user.getId(), hBus.getId());
            refreshUser();
        } else {
            throw new NoSuchBusException();
        }

    }

    private void refreshUser() {
        HUser userById = userDAO.getUserByLogin(securityService.getLogedInUser().getLogin());
        securityService.logout();
        securityService.login(new User(userById));
    }


}