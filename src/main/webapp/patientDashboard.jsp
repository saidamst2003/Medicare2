<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.io.IOException" %>

<%
    // Vérification de la session
    HttpSession sess = request.getSession(false);
    if (sess == null || sess.getAttribute("id") == null || !"patient".equalsIgnoreCase((String) sess.getAttribute("role"))) {
        response.sendRedirect("login.jsp?error=Session expirée, veuillez vous reconnecter.");
        return;
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de Bord - Patient</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center">Bienvenue, <%= sess.getAttribute("full_name") %> (Patient) </h2>
    <p class="text-center">Gérez vos rendez-vous médicaux ici.</p>

    <div class="text-center">
        <input type="submit" value="logout">
    </div>
</div>
</body>
</html>
