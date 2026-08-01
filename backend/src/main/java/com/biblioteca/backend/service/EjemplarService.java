package com.biblioteca.backend.service;

import com.biblioteca.backend.dto.EjemplarRequestDTO;
import com.biblioteca.backend.exception.BadRequestException;
import com.biblioteca.backend.exception.ResourceNotFoundException;
import com.biblioteca.backend.model.Ejemplar;
import com.biblioteca.backend.model.Libro;
import com.biblioteca.backend.repository.EjemplarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EjemplarService {

    private final EjemplarRepository ejemplarRepository;
    private final LibroService libroService;

    public EjemplarService(EjemplarRepository ejemplarRepository, LibroService libroService) {
        this.ejemplarRepository = ejemplarRepository;
        this.libroService = libroService;
    }

    public Ejemplar crear(EjemplarRequestDTO dto) {
        if (ejemplarRepository.existsByCodigoInventario(dto.getCodigoInventario())) {
            throw new BadRequestException("Ya existe un ejemplar con el código de inventario: " + dto.getCodigoInventario());
        }
        Libro libro = libroService.obtenerPorId(dto.getLibroId());

        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setCodigoInventario(dto.getCodigoInventario());
        ejemplar.setLibro(libro);
        ejemplar.setDisponible(true);

        return ejemplarRepository.save(ejemplar);
    }

    public List<Ejemplar> listarDisponiblesPorIsbn(String isbn) {
        return ejemplarRepository.findByLibroIsbnAndDisponibleTrue(isbn);
    }

    public List<Ejemplar> listarTodosPorIsbn(String isbn) {
        return ejemplarRepository.findByLibroIsbn(isbn);
    }

    public Ejemplar obtenerPorId(Long id) {
        return ejemplarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ejemplar no encontrado con ID: " + id));
    }
}