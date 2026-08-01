# Sistema de Gestión de Biblioteca API

Proyecto fullstack desarrollado como solución a la prueba técnica. Incluye gestión de Usuarios, Libros, Ejemplares y Préstamos con validación estricta de reglas de negocio.

## 🛠️ Tecnologías Utilizadas

* **Backend:** Java 17, Spring Boot 3, Spring Data JPA, Hibernate, Swagger/OpenAPI, Maven.
* **Frontend:** React, Vite, Axios, JavaScript.
* **Base de Datos:** MySQL 8.0.
* **DevOps / Despliegue:** Docker, Docker Compose, Nginx.

---

## 🚀 Instrucciones de Despliegue con Docker

Para ejecutar todo el ecosistema (Base de Datos + Backend + Frontend) con un solo comando:

1. Asegúrate de tener **Docker Desktop** abierto.
2. Abre la terminal en la raíz del proyecto principal.
3. Ejecuta:

```bash
docker compose up --build -d