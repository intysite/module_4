package com.instagram;

import org.hibernate.Session;

public class InstDao {
    public void addUser(Session session, String name, String password) {
        session.beginTransaction();
        User user = new User(name, password);
        session.save(user);
        session.getTransaction().commit();
        session.clear();
    }

    public void addPost(Session session, String text, Integer userId) {
        session.beginTransaction();
        Post post = new Post(text, session.get(User.class, userId));
        session.save(post);
        session.getTransaction().commit();
        session.clear();
    }

    public void addComment(Session session, String text, Integer postId) {
        session.beginTransaction();
        Post post = session.get(Post.class, postId);
        User user = post.getUser();
        Comment comment = new Comment(text, user, post);
        session.save(comment);
        session.getTransaction().commit();
        session.clear();
    }
}
