package org.example;

import org.example.dao.InstagramDAO;

import java.sql.SQLException;

public class Application {

    public static void main(String[] args) throws SQLException {
        InstagramDAO instagramDAO = new InstagramDAO();
       /* instagramDAO.createTables(); //создание таблиц
        instagramDAO.addUser("Olew", "123d"); //добавление пользователя
        instagramDAO.addPost("ddw", 2); //Добавление поста
        instagramDAO.addComment("ddw", 1, 2); //Добавление комента
        instagramDAO.addLike(1, 2, 2); // Добавление лайка
        instagramDAO.getStatistics(); // Коллчество записей*/
        instagramDAO.getInformationByID(5); // Получить информацию по id
    }
}
