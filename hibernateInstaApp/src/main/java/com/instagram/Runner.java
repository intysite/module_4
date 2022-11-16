package com.instagram;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Slf4j
public class Runner {
    public static void main(String[] args) {
        try(SessionFactory sessionFactory = HibernateConfig.createSessionFactory();
        Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            User user = new User("Alex", "1234");
            session.save(user);
            Post post = new Post("Один", user);
            session.save(post);
            Comment comment = new Comment("Two", user, post);
            session.save(comment);
            session.getTransaction().commit();
        }
    }
}
