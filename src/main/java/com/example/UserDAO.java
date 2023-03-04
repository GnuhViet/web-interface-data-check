package com.example;

import com.example.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public List<User> getUsers() {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from User", User.class)
                .getResultList();
    }
}
