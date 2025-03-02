package com.doctorrv.servlets;


import java.io.IOException;

import com.doctorrv.dao.UserDAO;
import com.doctorrv.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class registration
 */
@WebServlet("/registration")
public class RegistrServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String fullname = request.getParameter("full_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phoneNumber = request.getParameter("phone");
        String role = request.getParameter("role");
        String speciality = request.getParameter("speciality");  // Only for doctor role

        User user = new User();
        user.setFullName(fullname);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setRole(role);

        // Call the UserDao to insert the user and doctor if the role is "DOCTOR"
        UserDAO userDao = new UserDAO();
        int result = userDao.addUser(user);

        if (result > 0) {
            response.sendRedirect("login.jsp");
        } else {
            response.sendRedirect("registration.jsp?error=1");
        }
    }
}
