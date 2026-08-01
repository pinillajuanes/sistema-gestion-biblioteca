package com.biblioteca.backend.controller;

import com.biblioteca.backend.dto.EjemplarRequestDTO;
import com.biblioteca.backend.model.Ejemplar;
import com.biblioteca.backend.service.EjemplarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejemplares")
public class EjemplarController {

    private final EjemplarService ejemplarService;

    public EjemplarController(EjemplarService ejemplarService) {
        this.ejemplarService = ejemplarService;
    }

    @PostMapping
    public ResponseEntity<Ejemplar> crear(@RequestBody EjemplarRequestDTO dto) {
        return new ResponseEntity<>(ejemplarService.crear(dto), HttpStatus.CREATED);
    }

    // Endpoints requeridos por el PDF: Listar ejemplares por ISBN
    @GetMapping("/disponibles/{isbn}")
    public ResponseEntity<List<Ejemplar>> listarDisponiblesPorIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(ejemplarService.listarDisponiblesPorIsbn(isbn));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<List<Ejemplar>> listarTodosPorIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(ejemplarService.listarTodosPorIsbn(isbn));
    }
}