<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil - Medicare</title>
    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #F8BBD0; /* Rose clair */
        }
        .container {
            margin-top: 100px;
        }
        .btn-primary {
            background-color: #007BFF; /* Bleu */
            border: none;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container text-center">
    <h1 class="mb-4">Bienvenue sur Medicare</h1>
    <p class="lead">Système de réservation de rendez-vous médicaux</p>
    <a href="login.jsp" class="btn btn-primary me-3">Se connecter</a>
    <a href="registration.jsp" class="btn btn-outline-dark">S'inscrire</a>

</div>
</body>
</html>
