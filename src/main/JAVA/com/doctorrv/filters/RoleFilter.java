package com.doctorrv.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(urlPatterns = {"/doctorDashboard/*", "/patientDashboard/*"})
public class RoleFilter implements Filter {

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

        // URL de la requête
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        if (session != null && session.getAttribute("role") != null) {
            String userRole = (String) session.getAttribute("role");

            if (requestURI.startsWith(contextPath + "/doctorDashboard/") && !"doctor".equals(userRole)) {
                // Accès non autorisé à la zone médecin
                httpResponse.sendRedirect(contextPath + "/access-denied");
                return;
            }

            if (requestURI.startsWith(contextPath + "/patientDashboard/") && !"patient".equals(userRole)) {
                // Accès non autorisé à la zone patient
                httpResponse.sendRedirect(contextPath + "/access-denied");
                return;
            }

            // Autoriser l'accès, continuer la chaîne de filtres
            chain.doFilter(request, response);
        } else {
            // Session expirée ou aucun rôle disponible
            httpResponse.sendRedirect(contextPath + "/login");
        }
    }

    @Override
    public void destroy() {
        // Nettoyage des ressources
    }
}
