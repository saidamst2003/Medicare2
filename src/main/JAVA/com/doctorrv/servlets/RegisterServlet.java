package com.doctorrv.servlet;

import com.doctorrv.dao.UserDAO;
import com.doctorrv.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Afficher le formulaire d'inscription
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupérer les données du formulaire
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String phoneNumber = request.getParameter("phoneNumber");
        String role = request.getParameter("role");
        String specialization = request.getParameter("specialization"); // Pour les médecins

        // Valider les données
        boolean hasErrors = false;

        if (fullName == null || fullName.trim().isEmpty()) {
            request.setAttribute("errorFullName", "Le nom complet est obligatoire");
            hasErrors = true;
        }

        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            request.setAttribute("errorEmail", "Veuillez fournir une adresse email valide");
            hasErrors = true;
        } else {
            // Vérifier si l'email existe déjà
            User existingUser = userDAO.getUserByEmail(email);
            if (existingUser != null) {
                request.setAttribute("errorEmail", "Cette adresse email est déjà utilisée");
                hasErrors = true;
            }
        }

        if (password == null || password.length() < 8) {
            request.setAttribute("errorPassword", "Le mot de passe doit contenir au moins 8 caractères");
            hasErrors = true;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorConfirmPassword", "Les mots de passe ne correspondent pas");
            hasErrors = true;
        }

        if (role == null || (!role.equals("patient") && !role.equals("doctor"))) {
            request.setAttribute("errorRole", "Veuillez sélectionner un rôle valide");
            hasErrors = true;
        }

        if ("doctor".equals(role) && (specialization == null || specialization.trim().isEmpty())) {
            request.setAttribute("errorSpecialization", "La spécialisation est obligatoire pour les médecins");
            hasErrors = true;
        }

        // S'il y a des erreurs, réafficher le formulaire
        if (hasErrors) {
            // Conserver les valeurs saisies
            request.setAttribute("fullName", fullName);
            request.setAttribute("email", email);
            request.setAttribute("phoneNumber", phoneNumber);
            request.setAttribute("role", role);
            request.setAttribute("specialization", specialization);

            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        // Créer un nouvel utilisateur
        User newUser = new User(fullName, email, password, phoneNumber, role);
        userDAO.addUser(newUser);

        // Si c'est un médecin, ajouter la spécialisation dans une table séparée
        // (Nécessiterait un DAO supplémentaire pour les médecins)
        if ("doctor".equals(role) && specialization != null && !specialization.isEmpty()) {
            // Code pour ajouter la spécialisation du médecin
            // doctorDAO.addSpecialization(email, specialization);
        }

        // Connexion automatique après inscription
        HttpSession session = request.getSession();
        session.setAttribute("user", newUser);
        session.setAttribute("userId", newUser.getId());
        session.setAttribute("userRole", newUser.getRole());
        session.setAttribute("userEmail", newUser.getEmail());
        session.setAttribute("userName", newUser.getFullName());

        // Redirection en fonction du rôle
        if ("doctor".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/doctor/dashboard");
        } else {
            response.sendRedirect(request.getContextPath() + "/patient/dashboard");
        }
    }
}