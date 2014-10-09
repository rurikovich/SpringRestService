package com.test.mvc.dao;

/**
 * Created by rurik on 10/7/14.
 */

import com.test.mvc.security.SuperSecurityService;
import com.test.mvc.model.HBus;
import com.test.mvc.model.HUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class UserDAOImpl implements UserDAO {

    private SuperSecurityService securityService;

    @Autowired(required = true)
    @Qualifier(value = "securityService")
    public void setSecurityService(SuperSecurityService securityService) {
        this.securityService = securityService;
    }

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public HUser getUserById(int id) {
        Session session = sessionFactory.getCurrentSession();
        HUser hUser = (HUser) session.load(HUser.class, new Integer(id));
        return hUser;

    }

    @Override
    public HUser getUserByLogin(String login) {
        Session session = this.sessionFactory.openSession();
        List users = session.createCriteria(HUser.class).add(Expression.like("login", login)).list();
        session.close();
        if (users.size() > 0) {
            return (HUser) users.get(0);
        }
        return null;

    }

    @Override
    public void save(HUser user) {
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(user);
        session.close();
    }

    @Override
    public void addUser(String login, int money) {
        Session session = sessionFactory.openSession();
        HUser hUser=new HUser(login,money);
        session.saveOrUpdate(hUser);
        session.close();
    }


    public void buyBus(int userId, int busId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            HUser hUser = (HUser) session.get(HUser.class, userId);
            HBus hBus = (HBus) session.get(HBus.class, busId);
            int money_after_buy = hUser.getMoney() - hBus.getCost();
            if (money_after_buy > 0 && !hUser.getBuss().contains(hBus)) {
                hUser.getBuss().add(hBus);
                hUser.setMoney(money_after_buy);
                session.save(hUser);
            }
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void sellBus(int userId, int busId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            HUser hUser = (HUser) session.get(HUser.class, userId);
            HBus hBus = (HBus) session.get(HBus.class, busId);
            if (hUser.getBuss().contains(hBus)) {
                int money_after_sell = hUser.getMoney() + (int) (hBus.getCost() * 0.85);
                hUser.setMoney(money_after_sell);
                hUser.getBuss().remove(hBus);
                session.save(hUser);
            }


            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


}