package com.test.mvc.dao;

/**
 * Created by rurik on 10/7/14.
 */

import com.test.mvc.model.HBus;
import com.test.mvc.model.HUser;
import com.test.mvc.valueObjects.Bus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;

import java.util.ArrayList;
import java.util.List;


public class BusDAOImpl implements BusDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Bus> list() {
        Session session = this.sessionFactory.openSession();
        List<HBus> HBusList = session.createQuery("from HBus").list();
        List<Bus> buses=new ArrayList<Bus>();
        for(HBus hBus : HBusList){
            buses.add(new Bus(hBus));
        }
        session.close();
        return buses;
    }

    @Override
    public HBus getBusByName(String name) {
        Session session = this.sessionFactory.openSession();
        List buss = session.createCriteria(HBus.class).add(Expression.like("name", name)).list();
        session.close();
        if (buss.size() > 0) {
            return (HBus) buss.get(0);
        }
        return null;
    }

    @Override
    public void addNewBus(String name, int cost) {
        Session session = sessionFactory.openSession();
        HBus hBus=new HBus(name,cost);

        session.saveOrUpdate(hBus);
        session.close();
    }


}