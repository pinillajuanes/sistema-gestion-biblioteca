# Sistema de Gestión de Biblioteca API

Proyecto fullstack desarrollado como solución a la prueba técnica. Incluye la gestión integral de Usuarios, Libros, Ejemplares y Préstamos, implementando validaciones de reglas de negocio en la API REST y una interfaz web en React.

---

## 🛠️ Tecnologías Utilizadas

* **Backend:** Java 17, Spring Boot 3.5.4, Spring Data JPA, Hibernate, OpenAPI/Swagger, Maven.
* **Frontend:** React, Vite, Axios, HTML5, CSS3, JavaScript.
* **Base de Datos:** MySQL 8.0.
* **DevOps & Contenedores:** Docker, Docker Compose, Nginx.

---

## ⚙️ Variables de Entorno

El proyecto utiliza las siguientes variables de entorno para su configuración y despliegue desacoplado:

### Backend (/backend)
* DB_URI: jdbc:mysql://db:3306/biblioteca_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
* DB_USER: root
* DB_PASSWORD: root
* DB_DRIVER: com.mysql.cj.jdbc.Driver

### Frontend (/frontend)
* VITE_API_URL: http://localhost:8080/api

---

## 🚀 Instrucciones de Despliegue con Docker

Para levantar todo el ecosistema (Base de Datos MySQL + Backend Spring Boot + Frontend React con Nginx) sigue estos sencillos pasos:

1. Clonar el repositorio y entrar a la raíz del proyecto:
   git clone <URL_DE_TU_REPOSITORIO_GITHUB>
   cd <NOMBRE_DE_TU_CARPETA>

2. Desplegar la aplicación completa:
   docker compose up --build -d

3. Verificar el estado de los contenedores:
   docker ps

4. Para detener y remover los contenedores:
   docker compose down

*(Cumple con el requerimiento de menos de 10 comandos para el despliegue).*

---

## 🌐 Accesos a la Aplicación

Una vez levantados los servicios con Docker, se puede acceder a:

* Frontend (Interfaz Web): http://localhost
* Backend API REST Base: http://localhost:8080/api
* Documentación Swagger UI (API Rest): http://localhost:8080/swagger-ui/index.html

---

## 💾 Datos de Prueba (db-dump/)

El repositorio incluye la carpeta db-dump/ con el archivo de respaldo (schema.sql / .dump), el cual se ejecuta e inicializa automáticamente al levantar el contenedor de la base de datos MySQL en Docker Compose.

---

## 🧪 Reglas de Negocio Implementadas

* Restricción de Préstamo Único Activo: Un usuario no puede registrar un nuevo préstamo si ya cuenta con un ejemplar en estado ACTIVO. La API responde con un estado 400 Bad Request informando la restricción, la cual se captura y muestra visualmente en la interfaz web.
* Gestión de Ejemplares y Disponibilidad: Cada libro cuenta con ejemplares físicos identificables por código de inventario. La disponibilidad del ejemplar se actualiza automáticamente a false al prestarse y vuelve a true al realizarse la devolución.
* Consulta por ISBN: Endpoint y vista dedicados para listar únicamente los ejemplares disponibles según el ISBN del libro.
