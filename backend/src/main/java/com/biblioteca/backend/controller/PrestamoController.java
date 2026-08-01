package com.biblioteca.backend.controller;

import com.biblioteca.backend.dto.PrestamoRequestDTO;
import com.biblioteca.backend.model.Prestamo;
import com.biblioteca.backend.service.PrestamoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public ResponseEntity<Prestamo> registrarPrestamo(@RequestBody PrestamoRequestDTO dto) {
        return new ResponseEntity<>(prestamoService.registrarPrestamo(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<Prestamo> devolverPrestamo(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.devolverPrestamo(id));
    }

    @GetMapping
    public ResponseEntity<List<Prestamo>> listarTodos() {
        return ResponseEntity.ok(prestamoService.listarTodos());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Prestamo>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(prestamoService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/libro/{libroId}")
    public ResponseEntity<List<Prestamo>> listarPorLibro(@PathVariable Long libroId) {
        return ResponseEntity.ok(prestamoService.listarPorLibro(libroId));
    }
}