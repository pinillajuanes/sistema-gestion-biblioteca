CREATE DATABASE IF NOT EXISTS biblioteca_db;
USE biblioteca_db;

-- 1. Crear tablas si no existen
CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    fecha_nacimiento DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS libro (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    isbn VARCHAR(255) NOT NULL UNIQUE,
    edicion VARCHAR(255),
    fecha_publicacion DATE,
    autor VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS ejemplar (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_inventario VARCHAR(255) NOT NULL UNIQUE,
    disponible BOOLEAN DEFAULT TRUE,
    libro_id BIGINT,
    FOREIGN KEY (libro_id) REFERENCES libro(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS prestamo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_prestamo DATE NOT NULL,
    fecha_devolucion DATE NOT NULL,
    estado_prestamo VARCHAR(50) NOT NULL,
    usuario_id BIGINT,
    ejemplar_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (ejemplar_id) REFERENCES ejemplar(id) ON DELETE CASCADE
);

-- 2. Insertar datos de prueba
INSERT INTO usuario (id, nombre, apellido, email, fecha_nacimiento) VALUES
(1, 'Juan', 'Perez', 'juan.perez@example.com', '1998-05-15'),
(2, 'Maria', 'Gomez', 'maria.gomez@example.com', '2001-10-20');

INSERT INTO libro (id, titulo, isbn, edicion, fecha_publicacion, autor) VALUES
(1, 'Cien Años de Soledad', '978-0307474728', '1ra Edición', '1967-05-30', 'Gabriel García Márquez'),
(2, 'Don Quijote de la Mancha', '978-8424116026', '2da Edición', '1605-01-16', 'Miguel de Cervantes');

INSERT INTO ejemplar (id, codigo_inventario, disponible, libro_id) VALUES
(1, 'ISBN-0307474728-01', 1, 1),
(2, 'ISBN-0307474728-02', 1, 1),
(3, 'ISBN-8424116026-01', 1, 2);
