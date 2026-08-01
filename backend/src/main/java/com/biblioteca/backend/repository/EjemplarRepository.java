package com.biblioteca.backend.repository;

import com.biblioteca.backend.model.Ejemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar, Long> {
    // Requerimiento del PDF: listar ejemplares disponibles por ISBN[cite: 1]
    List<Ejemplar> findByLibroIsbnAndDisponibleTrue(String isbn);
    List<Ejemplar> findByLibroIsbn(String isbn);
    boolean existsByCodigoInventario(String codigoInventario);
}