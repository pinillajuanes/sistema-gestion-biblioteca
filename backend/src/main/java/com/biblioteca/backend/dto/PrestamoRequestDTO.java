package com.biblioteca.backend.dto;

import java.time.LocalDate;

public class PrestamoRequestDTO {
    private Long usuarioId;
    private Long ejemplarId;
    private LocalDate fechaDevolucion;

    public PrestamoRequestDTO() {}

    public PrestamoRequestDTO(Long usuarioId, Long ejemplarId, LocalDate fechaDevolucion) {
        this.usuarioId = usuarioId;
        this.ejemplarId = ejemplarId;
        this.fechaDevolucion = fechaDevolucion;
    }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getEjemplarId() { return ejemplarId; }
    public void setEjemplarId(Long ejemplarId) { this.ejemplarId = ejemplarId; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
}