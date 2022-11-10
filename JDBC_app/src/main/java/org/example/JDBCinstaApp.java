package org.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Stack;
import java.util.stream.Collectors;

public class JDBCinstaApp {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "postgre";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ) {
            statement.execute(readSqlFile("create_tables.sql"));

            fillDataUsers(statement, "'Leha'", "'qwerty'", "'2022-11-05 09:18:34.543'");
            fillDataUsers(statement, "'Kuka'", "'1234'", "'2022-11-07 13:44:01.074'");

            fillDataPost(statement, "'Вот и лето прошло...'", "'2022-11-06 10:51:07.887'", 1);
            fillDataPost(statement, "'Словно и не бывало...'", "'2022-11-08 22:49:46.359'", 2);

            fillDataComment(statement, "'Ух ты!'", 1, 1, "'2022-11-06 10:52:04.447'");
            fillDataComment(statement, "'Свежо'", 1, 2, "'2022-11-08 23:07:35.537'");

            fillDataLikes(statement, 1, 1, 1);
            fillDataLikes(statement, 1, 1, 1);
            fillDataLikes(statement, 2, 1, 1);

            getStatistics(statement);

            getUsersInfo(statement, 2);
        }
    }

    public static String readSqlFile(String filename) {
        InputStream resource = JDBCinstaApp.class.getClassLoader().getResourceAsStream(filename);
        return new BufferedReader(new InputStreamReader(resource)).lines().collect(Collectors.joining(""));
    }

    public static void fillDataUsers(Statement statement, String name,
                                     String password, String timestamp) throws SQLException {
        statement.execute("INSERT INTO users (name, password, created_at) VALUES (" +
                name + "," +
                password + "," +
                timestamp + ");");
    }

    public static void fillDataPost(Statement statement, String text,
                                    String timestamp, int users_id) throws SQLException {
        statement.execute("INSERT INTO post (text, created_at, users_id) VALUES (" +
                text + "," +
                timestamp + "," +
                users_id + ");");
    }

    public static void fillDataComment(Statement statement, String text,
                                       int post_id, int users_id, String timestamp) throws SQLException {
        statement.execute("INSERT INTO comment (text, post_id, users_id, created_at) VALUES (" +
                text + "," +
                post_id + "," +
                users_id + "," +
                timestamp + ");");
    }

    public static void fillDataLikes(Statement statement, int users_id,
                                     int post_id, int comment_id) throws SQLException {
        statement.execute("INSERT INTO likes (users_id, post_id, comment_id) VALUES (" +
                users_id + "," +
                post_id + "," +
                comment_id + ");");
    }

    public static void getStatistics(Statement statement) throws SQLException {
        String [] tableNames = {"users", "post", "comment", "likes"};
        int [] counts = new int[4];
        for (int i = 0; i < 4; i++) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT (*) FROM " + tableNames[i]);
            while (resultSet.next()) {
                counts[i] = Integer.parseInt(resultSet.getString(1));
            }
        }
        System.out.println("Всего: количество пользователей - " + counts[0]);
        System.out.println("количество постов - " + counts[1]);
        System.out.println("количество комментариев - " + counts[2]);
        System.out.println("количество лайков  - " + counts[3]);
    }

    public static void getUsersInfo(Statement statement, int id) throws SQLException {
        ResultSet userDataSet = statement.executeQuery(
                "SELECT u.name, u.created_at " +
                        "FROM users AS u " +
                        "WHERE id = " + id);

        while (userDataSet.next()) {
            System.out.println("Пользователь - " + userDataSet.getString(1));
            System.out.println("Дата создания - " + userDataSet.getString(2));
        }

        ResultSet postDataSet = statement.executeQuery(
                "SELECT p.created_at " +
                        "FROM post AS p " +
                        "WHERE users_id = " + id +
                        " LIMIT 1;");
        while (postDataSet.next()) {
            System.out.println("Самый первый пост - " + postDataSet.getString(1));
        }

        ResultSet commentDataSet = statement.executeQuery(
                "SELECT COUNT (*) " +
                        "FROM comment " +
                        "WHERE users_id = " + id + ";");
        while (commentDataSet.next()) {
            System.out.println("Количество комментов - " + commentDataSet.getString(1));
        }
    }
}
