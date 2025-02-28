package com.doctorrv.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@WebFilter(urlPatterns = {"/*"})
public class SessionFilter implements Filter {

    private static final long MAX_INACTIVE_SESSION_TIME = 30 * 60 * 1000; // 30 minutes

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

        // Vérifier si c'est une page publique
        boolean isPublicPage = requestURI.equals(contextPath + "/login") ||
                requestURI.equals(contextPath + "/register") ||
                requestURI.equals(contextPath + "/") ||
                requestURI.contains("/resources/") ||
                requestURI.contains("/public/");

        if (session != null) {
            // Vérifier l'expiration de la session
            Date currentTime = new Date();
            long lastAccessTime = session.getLastAccessedTime();

            if (currentTime.getTime() - lastAccessTime > MAX_INACTIVE_SESSION_TIME) {
                // Session expirée, invalider la session
                session.invalidate();
                httpResponse.sendRedirect(contextPath + "/login?expired=true");
                return;
            }

            // Rediriger les utilisateurs connectés qui tentent d'accéder aux pages publiques
            if (isPublicPage && session.getAttribute("user") != null) {
                String userRole = (String) session.getAttribute("userRole");
                if ("doctor".equals(userRole)) {
                    httpResponse.sendRedirect(contextPath + "/doctor/dashboard");
                } else {
                    httpResponse.sendRedirect(contextPath + "/patient/dashboard");
                }
                return;
            }
        }

        // Continuer la chaîne de filtres
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Nettoyage des ressources
    }
}