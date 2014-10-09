package com.test.mvc.dao;

/**
 * Created by rurik on 10/7/14.
 */

import com.test.mvc.model.HBus;
import com.test.mvc.valueObjects.Bus;

import java.util.List;


public interface BusDAO {
    public List<Bus> list();

    public HBus getBusByName(String name);
    public void addNewBus(String name,int cost);

}
