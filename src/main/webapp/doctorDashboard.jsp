<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="java.io.*, java.util.*" %>
<%@ page session="true" %>
<%
    String role = (String) session.getAttribute("role");
    String fullName = (String) session.getAttribute("full_name");
    out.println("Debug - Session ID : " + session.getId() + "<br>");
    out.println("Role récupéré : " + session.getAttribute("role") + "<br>");
    out.println("FullName récupéré : " + session.getAttribute("full_name") + "<br>");


    if (role == null || fullName == null) {
        response.sendRedirect("login.jsp?error=sessionExpired");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
<h1>welcome to the doctorDashboard</h1>
</body>
</html>