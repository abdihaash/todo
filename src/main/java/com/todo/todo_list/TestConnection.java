package com.todo.todo_list;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/todo-list?createDatabaseIfNotExist=true";
        String username = "root";
        String password = "root";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to the database!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

