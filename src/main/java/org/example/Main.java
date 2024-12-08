package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

        createDatabase();

        CourseDAO courseDAO = new CourseDAO();

        courseDAO.addCourse("Math 101", 40);

        Course course = courseDAO.getCourse(1);
        if (course != null) {
            System.out.println("Course: " + course.getTitle());
        } else {
            System.out.println("Course not found!");
        }

        courseDAO.updateCourse(1, "Math 102", 45);

        courseDAO.deleteCourse(1);

        courseDAO.close();
    }

    private static void createDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "123456")) {
            Statement stmt = connection.createStatement();
            // Создаём базу данных, если её нет
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS Schooldb";
            stmt.executeUpdate(createDatabaseSQL);
            System.out.println("Database created or already exists.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}