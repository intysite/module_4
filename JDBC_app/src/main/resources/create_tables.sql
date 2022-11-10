CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY,
                                  name VARCHAR(100) NOT NULL UNIQUE,
                                  password VARCHAR(100) NOT NULL,
                                  created_at TIMESTAMP);

CREATE TABLE IF NOT EXISTS post (id SERIAL PRIMARY KEY,
                                 text TEXT NOT NULL,
                                 created_at TIMESTAMP,
                                 users_id INT NOT NULL REFERENCES users(id));

CREATE TABLE IF NOT EXISTS comment (id SERIAL PRIMARY KEY,
                                    text TEXT NOT NULL,
                                    post_id INT NOT NULL REFERENCES post(id),
                                    users_id INT NOT NULL REFERENCES users(id),
                                    created_at TIMESTAMP);

CREATE TABLE IF NOT EXISTS likes (id SERIAL PRIMARY KEY,
                                  users_id INT NOT NULL REFERENCES users(id),
                                  post_id INT REFERENCES post(id),
                                  comment_id INT REFERENCES comment(id),
                                  CHECK (post_id IS NOT NULL OR comment_id IS NOT NULL));