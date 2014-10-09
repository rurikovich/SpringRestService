package com.test.mvc.service;

/**
 * Created by rurik on 10/7/14.
 */

import com.test.mvc.exceptions.NoSuchBusException;
import com.test.mvc.exceptions.NotEnoughMoneyException;
import com.test.mvc.valueObjects.Bus;
import com.test.mvc.valueObjects.User;

import java.util.List;

public interface BusService {

    public List<Bus> listBuss();

    public void buyBus(User user, String busName) throws NotEnoughMoneyException;

    public void sellBus(User user, String busName) throws NoSuchBusException;

}
