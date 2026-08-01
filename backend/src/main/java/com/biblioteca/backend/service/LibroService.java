package com.biblioteca.backend.service;

import com.biblioteca.backend.exception.BadRequestException;
import com.biblioteca.backend.exception.ResourceNotFoundException;
import com.biblioteca.backend.model.Libro;
import com.biblioteca.backend.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> listarTodos() {
        return libroRepository.findAll();
    }

    public Libro obtenerPorId(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ID: " + id));
    }

    public Libro crear(Libro libro) {
        if (libroRepository.existsByIsbn(libro.getIsbn())) {
            throw new BadRequestException("Ya existe un libro registrado con el ISBN: " + libro.getIsbn());
        }
        return libroRepository.save(libro);
    }

    public Libro actualizar(Long id, Libro libroActualizado) {
        Libro libroExistente = obtenerPorId(id);

        if (!libroExistente.getIsbn().equals(libroActualizado.getIsbn()) &&
                libroRepository.existsByIsbn(libroActualizado.getIsbn())) {
            throw new BadRequestException("El ISBN " + libroActualizado.getIsbn() + " ya está en uso.");
        }

        libroExistente.setTitulo(libroActualizado.getTitulo());
        libroExistente.setIsbn(libroActualizado.getIsbn());
        libroExistente.setEdicion(libroActualizado.getEdicion());
        libroExistente.setFechaPublicacion(libroActualizado.getFechaPublicacion());
        libroExistente.setAutor(libroActualizado.getAutor());

        return libroRepository.save(libroExistente);
    }

    public void eliminar(Long id) {
        Libro libro = obtenerPorId(id);
        libroRepository.delete(libro);
    }
}