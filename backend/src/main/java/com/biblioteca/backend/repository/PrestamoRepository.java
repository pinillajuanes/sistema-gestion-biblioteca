package com.biblioteca.backend.repository;

import com.biblioteca.backend.model.Prestamo;
import com.biblioteca.backend.model.enums.EstadoPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    // Requerimientos del PDF: listar préstamos por usuario y por libro[cite: 1]
    List<Prestamo> findByUsuarioId(Long usuarioId);
    List<Prestamo> findByEjemplarLibroId(Long libroId);

    // Requerimiento clave: validar que un usuario no tenga más de un préstamo activo[cite: 1]
    boolean existsByUsuarioIdAndEstadoPrestamo(Long usuarioId, EstadoPrestamo estadoPrestamo);
}