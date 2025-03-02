package com.doctorrv.servlets;


import com.doctorrv.dao.loginDAO;
import com.doctorrv.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve login details
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        // Authenticate the user using LoginDao
        User user = loginDAO.authenticate(email, password);

        if (user != null) {
            System.out.println("User authenticated: " + user.getFullName() + ", Role: " + user.getRole());
            // Create a new session or retrieve an existing one
            HttpSession session = request.getSession(true); // true ensures session is created if it doesn't exist
            session.setAttribute("id", user.getId());
            session.setAttribute("full_name", user.getFullName());
            session.setAttribute("role", user.getRole());

            System.out.println("Session created with ID: " + session.getId());
            System.out.println("User logged in: " + user.getFullName());
            System.out.println("Role: " + user.getRole());

            // Redirect to the appropriate dashboard based on role
            if ("patient".equalsIgnoreCase(user.getRole())) {
                System.out.println(user.getRole());
                System.out.println("Redirection vers : patientDashboard.jsp");
                response.sendRedirect("patientDashboard.jsp");
            } else if ("DOCTOR".equalsIgnoreCase(user.getRole())) {
                System.out.println("Redirection vers : doctorDashboard.jsp");
                response.sendRedirect("doctorDashboard.jsp");
            } else {
                // Redirect to a default page or an error page if the role is unexpected
                response.sendRedirect("errorPage.jsp");
            }

        } else {
            System.out.println("Authentication failed: User not found");
            // Authentication failed, redirect back to login page with error message
            response.sendRedirect("login.jsp?error=1"); // You could also provide a detailed error message here
        }
    }
}
