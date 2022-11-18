package com.instagram;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Slf4j
public class Runner {
    public static void main(String[] args) {
        try(SessionFactory sessionFactory = HibernateConfig.createSessionFactory();
        Session session = sessionFactory.openSession();) {
            InstDao instDao = new InstDao();
            instDao.addUser(session, "Alex", "1234");
            instDao.addPost(session, "Один", 1);
            instDao.addComment(session, "Two", 1);
        }
    }
}
