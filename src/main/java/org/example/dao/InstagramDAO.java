package org.example.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.stream.Collectors;

public class InstagramDAO {

    private String url = "jdbc:postgresql://localhost:5432/postgres";

    private String user = "postgres";

    private String password = "556677";

    public void createTables() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();) {
            statement.execute(readSqlFile("create_tables.sql"));
        }
    }

    public void addUser(String name, String pass) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();) {
            statement.execute("INSERT INTO users (name,password,created_at) VALUES " +
                    "(" + "'" + name + "'" + "," + "'" + pass + "'" + "," + "CURRENT_TIMESTAMP);");
        }
    }

    public void addPost(String text, int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();) {
            statement.execute("INSERT INTO post (text,created_at, user_id) VALUES " +
                    "(" + "'" + text + "'" + ",CURRENT_TIMESTAMP," + id + ");");
        }
    }

    public void addComment(String text, int postId, int userId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();) {
            statement.execute("INSERT INTO comment (text,post_id,user_id,created_at) " +
                    "VALUES (" + "'" + text + "'," + postId + "," + userId + "," + "CURRENT_TIMESTAMP);");
        }
    }

    public void addLike(int userId, int postId, int commentId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();) {
            statement.execute("INSERT INTO likes (user_id,post_id,comment_id) " +
                    "VALUES (" + userId + "," + postId + "," + commentId + ");");
        }
    }

    public void getStatistics() throws SQLException {
        printStatistics("users");
        printStatistics("post");
        printStatistics("comment");
        printStatistics("likes");
    }

    public void getInformationByID(int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery("SELECT  users.name, users.created_at,post.text " +
                    "FROM users JOIN post ON users.id = post.user_id  \n" +
                    "WHERE user_id =" + id + "LIMIT 1");
            String res = printAllInformation(resultSet);
            if (res.equals("Пользователь не найден")) {
                System.out.println(res);
            } else {
                System.out.println(printAllInformation(resultSet) + getCountComment(id));
            }
        }
    }

    private String getCountComment(int id) throws SQLException {
        String res = "";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM comment\n" +
                    "where user_id =" + id + ";");
            while (resultSet.next()) {
                res += resultSet.getString(1);
            }
        }
        return "Колличество комментов пользователя: " + res;
    }

    private String printAllInformation(ResultSet resultSet) throws SQLException {
        String res = "";
        while (resultSet.next()) {
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                switch (i) {
                    case 1:
                        res += "Имя пользователя: " + resultSet.getString(i) + "\n";
                        break;
                    case 2:
                        res += "Дата регистрации пользователя: " + resultSet.getString(i) + "\n";
                        break;
                    case 3:
                        res += "Первый пост: " + resultSet.getString(i) + "\n";
                        break;
                }
            }
        }
        if (res.equals("")) {
            return "Пользователь не найден";
        }
        return res;
    }

    private void printStatistics(String tableName) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + tableName + ";");
            while (resultSet.next()) {
                System.out.println("Колличество записей в таблице " + tableName + ": " + resultSet.getString(1));
            }
        }
    }

    private String readSqlFile(String fileName) {
        InputStream resource = InstagramDAO.class.getClassLoader().getResourceAsStream(fileName);
        return new BufferedReader(new InputStreamReader(resource)).lines().collect(Collectors.joining(""));
    }
}
