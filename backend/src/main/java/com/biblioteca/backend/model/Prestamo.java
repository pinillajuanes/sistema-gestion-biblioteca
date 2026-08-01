package com.biblioteca.backend.model;

import com.biblioteca.backend.model.enums.EstadoPrestamo;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    @Column(name = "fecha_devolucion", nullable = false)
    private LocalDate fechaDevolucion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ejemplar_id", nullable = false)
    private Ejemplar ejemplar;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_prestamo", nullable = false)
    private EstadoPrestamo estadoPrestamo;

    public Prestamo() {}

    public Prestamo(Long id, LocalDate fechaPrestamo, LocalDate fechaDevolucion, Usuario usuario, Ejemplar ejemplar, EstadoPrestamo estadoPrestamo) {
        this.id = id;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.usuario = usuario;
        this.ejemplar = ejemplar;
        this.estadoPrestamo = estadoPrestamo;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Ejemplar getEjemplar() { return ejemplar; }
    public void setEjemplar(Ejemplar ejemplar) { this.ejemplar = ejemplar; }

    public EstadoPrestamo getEstadoPrestamo() { return estadoPrestamo; }
    public void setEstadoPrestamo(EstadoPrestamo estadoPrestamo) { this.estadoPrestamo = estadoPrestamo; }
}