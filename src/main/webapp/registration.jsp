<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription - Medicare</title>
    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #F8BBD0; /* Rose clair */
        }
        .container {
            margin-top: 50px;
            max-width: 500px;
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="text-center mb-4">Inscription</h2>
    <form action="register" method="post">
        <div class="mb-3">
            <label for="fullName" class="form-label">Nom complet</label>
            <input type="text" class="form-control" id="fullName" name="fullName" required>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" name="email" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Mot de passe</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>
        <div class="mb-3">
            <label for="confirmPassword" class="form-label">Confirmer le mot de passe</label>
            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
        </div>
        <div class="mb-3">
            <label for="phoneNumber" class="form-label">Numéro de téléphone</label>
            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" required>
        </div>
        <div class="mb-3">
            <label for="role" class="form-label">Rôle</label>
            <select class="form-select" id="role" name="role" required>
                <option value="patient">Patient</option>
                <option value="doctor">Médecin</option>
            </select>
        </div>
        <div class="mb-3" id="specializationField" style="display: none;">
            <label for="specialization" class="form-label">Spécialisation</label>
            <input type="text" class="form-control" id="specialization" name="specialization">
        </div>
        <button type="submit" class="btn btn-primary w-100">S'inscrire</button>
    </form>
    <div class="text-center mt-3">
        <a href="login.jsp">Déjà inscrit ? Se connecter</a>
    </div>
</div>

<script>
    document.getElementById("role").addEventListener("change", function() {
        var specializationField = document.getElementById("specializationField");
        if (this.value === "doctor") {
            specializationField.style.display = "block";
        } else {
            specializationField.style.display = "none";
        }
    });
</script>
</body>
</html>
