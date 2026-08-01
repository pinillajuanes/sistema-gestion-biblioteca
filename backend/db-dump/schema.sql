USE biblioteca_db;

INSERT INTO usuarios (nombre, apellido, email, fecha_nacimiento) VALUES
                                                                     ('Juan', 'Perez', 'juan.perez@example.com', '1998-05-15'),
                                                                     ('Maria', 'Gomez', 'maria.gomez@example.com', '2001-10-20');

INSERT INTO libros (titulo, isbn, edicion, fecha_publicacion, autor) VALUES
                                                                         ('Cien Años de Soledad', '978-0307474728', '1ra Edición', '1967-05-30', 'Gabriel García Márquez'),
                                                                         ('Don Quijote de la Mancha', '978-8424116026', '2da Edición', '1605-01-16', 'Miguel de Cervantes');

INSERT INTO ejemplares (codigo_inventario, disponible, libro_id) VALUES
                                                                     ('ISBN-0307474728-01', 1, 1),
                                                                     ('ISBN-0307474728-02', 1, 1),
                                                                     ('ISBN-8424116026-01', 1, 2);