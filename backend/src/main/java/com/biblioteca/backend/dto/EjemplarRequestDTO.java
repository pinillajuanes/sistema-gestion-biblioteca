package com.biblioteca.backend.dto;

public class EjemplarRequestDTO {
    private String codigoInventario;
    private Long libroId;

    public EjemplarRequestDTO() {}

    public EjemplarRequestDTO(String codigoInventario, Long libroId) {
        this.codigoInventario = codigoInventario;
        this.libroId = libroId;
    }

    public String getCodigoInventario() { return codigoInventario; }
    public void setCodigoInventario(String codigoInventario) { this.codigoInventario = codigoInventario; }

    public Long getLibroId() { return libroId; }
    public void setLibroId(Long libroId) { this.libroId = libroId; }
}