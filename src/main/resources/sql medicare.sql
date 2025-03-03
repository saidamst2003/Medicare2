CREATE DATABASE DoctorRV;
USE DoctorRV;
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    role ENUM('PATIENT', 'MEDECIN') NOT NULL,
    speciality VARCHAR(255) NULL,  -- NULL pour les patients
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

select * from users;
