package com.doctorrv.filters;


import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebFilter({ "/doctorDashboard.jsp" ,"/patientDashboard.jsp"})
public class SessionFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); // Ne crée pas une nouvelle session

        System.out.println(" SessionFilter appliqué à : " + req.getRequestURI());

        if (session == null) {
            System.out.println(" Aucune session trouvée !");
            res.sendRedirect("login.jsp?error=session");
            return;
        }

        Object userId = session.getAttribute("id");
        Object role = session.getAttribute("role");

        System.out.println("Session trouvée : " + session.getId());
        System.out.println("idUser dans session : " + userId);
        System.out.println("Role dans session : " + role);

        // Vérification que l'utilisateur est bien authentifié
        if (userId == null) {
            System.out.println(" idUser est NULL, redirection vers login.jsp");
            res.sendRedirect("login.jsp?error=session");
            return;
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {}
    public void destroy() {}
}
