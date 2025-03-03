package com.doctorrv.dao;


import com.doctorrv.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class loginDAO {
    public static User authenticate(String email , String password) {

        String query ="SELECT id, full_name,phone, role FROM users WHERE email = ? AND password = ?";
        User user = null;
        try {
            Connection connection = UserDAO.getConnection();

            PreparedStatement pdst = connection.prepareStatement(query);

            pdst.setString(1, email);
            pdst.setString(2, password);
            System.out.println("Executing query: " + pdst.toString()); // Log de la requête

            ResultSet rs = pdst.executeQuery();

            if(rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                System.out.println("the id is = " +user.getId());
                user.setFullName(rs.getString("full_name"));
                user.setPhoneNumber(rs.getString("phone"));
                user.setRole(rs.getString("role"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(user);
        return user;

    }


}

