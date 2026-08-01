package com.biblioteca.backend.service;

import com.biblioteca.backend.dto.PrestamoRequestDTO;
import com.biblioteca.backend.exception.BadRequestException;
import com.biblioteca.backend.exception.ResourceNotFoundException;
import com.biblioteca.backend.model.Ejemplar;
import com.biblioteca.backend.model.Prestamo;
import com.biblioteca.backend.model.Usuario;
import com.biblioteca.backend.model.enums.EstadoPrestamo;
import com.biblioteca.backend.repository.EjemplarRepository;
import com.biblioteca.backend.repository.PrestamoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final UsuarioService usuarioService;
    private final EjemplarService ejemplarService;
    private final EjemplarRepository ejemplarRepository;

    public PrestamoService(PrestamoRepository prestamoRepository,
                           UsuarioService usuarioService,
                           EjemplarService ejemplarService,
                           EjemplarRepository ejemplarRepository) {
        this.prestamoRepository = prestamoRepository;
        this.usuarioService = usuarioService;
        this.ejemplarService = ejemplarService;
        this.ejemplarRepository = ejemplarRepository;
    }

    @Transactional
    public Prestamo registrarPrestamo(PrestamoRequestDTO dto) {
        Usuario usuario = usuarioService.obtenerPorId(dto.getUsuarioId());
        Ejemplar ejemplar = ejemplarService.obtenerPorId(dto.getEjemplarId());

        // Restricción obligatoria: Un usuario no puede tener más de un préstamo activo
        boolean tienePrestamoActivo = prestamoRepository.existsByUsuarioIdAndEstadoPrestamo(usuario.getId(), EstadoPrestamo.ACTIVO);
        if (tienePrestamoActivo) {
            throw new BadRequestException("El usuario ya cuenta con un préstamo en estado ACTIVO.");
        }

        if (!ejemplar.getDisponible()) {
            throw new BadRequestException("El ejemplar seleccionado no se encuentra disponible.");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setEjemplar(ejemplar);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucion(dto.getFechaDevolucion());

        // Evaluar estado de acuerdo a la comparación de fechas
        prestamo.setEstadoPrestamo(calcularEstado(prestamo.getFechaDevolucion(), false));

        // Marcar ejemplar como no disponible
        ejemplar.setDisponible(false);
        ejemplarRepository.save(ejemplar);

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo devolverPrestamo(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new ResourceNotFoundException("Préstamo no encontrado con ID: " + prestamoId));

        if (prestamo.getEstadoPrestamo() == EstadoPrestamo.DEVUELTO) {
            throw new BadRequestException("Este préstamo ya fue devuelto previamente.");
        }

        prestamo.setEstadoPrestamo(EstadoPrestamo.DEVUELTO);

        // Liberar el ejemplar para futuros préstamos
        Ejemplar ejemplar = prestamo.getEjemplar();
        ejemplar.setDisponible(true);
        ejemplarRepository.save(ejemplar);

        return prestamoRepository.save(prestamo);
    }

    public List<Prestamo> listarTodos() {
        List<Prestamo> prestamos = prestamoRepository.findAll();
        // Actualizar estados automáticamente al consultar según la fecha actual
        prestamos.forEach(this::actualizarEstadoSiVencido);
        return prestamos;
    }

    public List<Prestamo> listarPorUsuario(Long usuarioId) {
        return prestamoRepository.findByUsuarioId(usuarioId);
    }

    public List<Prestamo> listarPorLibro(Long libroId) {
        return prestamoRepository.findByEjemplarLibroId(libroId);
    }

    private EstadoPrestamo calcularEstado(LocalDate fechaDevolucion, boolean devuelto) {
        if (devuelto) return EstadoPrestamo.DEVUELTO;
        if (LocalDate.now().isAfter(fechaDevolucion)) return EstadoPrestamo.VENCIDO;
        return EstadoPrestamo.ACTIVO;
    }

    private void actualizarEstadoSiVencido(Prestamo prestamo) {
        if (prestamo.getEstadoPrestamo() == EstadoPrestamo.ACTIVO && LocalDate.now().isAfter(prestamo.getFechaDevolucion())) {
            prestamo.setEstadoPrestamo(EstadoPrestamo.VENCIDO);
            prestamoRepository.save(prestamo);
        }
    }
}