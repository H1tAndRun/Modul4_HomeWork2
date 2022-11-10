package org.example;

import org.example.dao.InstagramDAO;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
        InstagramDAO.createTables(); //создание таблиц
        InstagramDAO.addUser("Olew", "123d"); //добавление пользователя
        InstagramDAO.addPost("ddw", 2); //Добавление поста
        InstagramDAO.addComment("ddw", 1, 2); //Добавление комента
        InstagramDAO.addLike(1,2,2); // Добавление лайка
        InstagramDAO.getStatistics(); // Коллчество записей
        InstagramDAO.getInformationByID(2); // Получить информацию по id
    }
}
