package com.doctorrv.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(urlPatterns = {"/doctor/*", "/patient/*", "/appointment/*", "/profile/*"})
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialisation du filtre
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Vérifier si l'utilisateur est connecté
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        // URL de la requête
        String requestURI = httpRequest.getRequestURI();

        if (isLoggedIn) {
            // L'utilisateur est connecté, continuer la chaîne de filtres
            chain.doFilter(request, response);
        } else {
            // L'utilisateur n'est pas connecté, rediriger vers la page de connexion
            httpRequest.setAttribute("error", "Veuillez vous connecter pour accéder à cette page");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() {
        // Nettoyage des ressources
    }
}