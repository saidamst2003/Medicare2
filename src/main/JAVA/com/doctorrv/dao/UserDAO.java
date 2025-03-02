package com.doctorrv.dao;

import com.doctorrv.model.User;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/doctorrv";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private static Connection connection;

    // Constructeur privé pour empêcher l'instanciation directe
    public UserDAO() {}

    // Méthode pour obtenir la connexion unique
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Chargement du driver
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connexion réussie !");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }


    public int addUser(User user) {
        int result =0;
        String sql = "INSERT INTO users (full_name, email, password, phone, role) VALUES (?, ?, ?, ?, ?)";
        try {
            getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, user.getFullName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassword());
                stmt.setString(4, user.getPhoneNumber());
                stmt.setString(5, user.getRole());
               result= stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void updateUser(User user) {
        String sql = "UPDATE users SET fullName=?, password=?, phoneNumber=?, role=? WHERE email=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getPhoneNumber());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String email) {
        String sql = "DELETE FROM users WHERE email=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
