package com.example.demo.service;

import com.example.demo.dto.OrdenDTO;
import com.example.demo.model.*;
import com.example.demo.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenService {
    private final AutoRepository autoRepository;
    private final OrdenRepository ordenRepository;
    private final SeguimientoRepository seguimientoRepository;
    private final EstadoRepository estadoRepository;
    private final UsuarioRepository usuarioRepository;

    public Orden crearOrden(OrdenDTO dto) {

        Auto auto = autoRepository.findById(dto.getAuto_id())
                .orElseThrow(() -> new RuntimeException("Auto no encontrado"));

        Estado estado = estadoRepository.findById(dto.getEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        // Crear la orden
        Orden orden = Orden.builder()
                .auto(auto)
                .numero_orden(generarNumeroOrden())
                .fecha_creacion(LocalDate.now())
                .ordenUsuarios(new ArrayList<>())
                .build();

        // Crear el seguimiento inicial
        Seguimiento seguimiento = Seguimiento.builder()
                .orden(orden)
                .estado(estado)
                .fecha_actualizacion(LocalDate.now())
                .build();

        // Relación bidireccional
        orden.getSeguimientos().add(seguimiento);

        return ordenRepository.save(orden);
    }

    private String generarNumeroOrden() {
        return "ORD-" + System.currentTimeMillis();
    }

    public List<Orden> getOrdenesPorEstados(List<Long> ids) {
        return ordenRepository.findOrdenesPorEstadoActual(ids);
    }

    public Orden asignarOrden(OrdenDTO dto) {

        // Obtener orden
        Orden orden = ordenRepository.findById(dto.getOrden_id())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        // Obtener seguimiento del día
        Seguimiento seguimientoHoy = orden.getSeguimientos().stream()
                .filter(s -> s.getFecha_actualizacion().isEqual(LocalDate.now()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No existe un seguimiento para hoy"));

        Estado estadoActual = seguimientoHoy.getEstado();

        if (estadoActual == null || estadoActual.getEstado_id() == null) {
            throw new RuntimeException("El seguimiento no tiene un estado asignado.");
        }

        // Obtener usuario a asignar
        Usuario usuario = usuarioRepository.findById(dto.getUsuario_id())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar estado según el rol del usuario
        Estado estadoAsignado;

        if (usuario.getRol().getRol_id() == 2) {
            estadoAsignado = estadoRepository.findById(2L)
                    .orElseThrow(() -> new RuntimeException("Estado 2 no encontrado"));
        } else if (usuario.getRol().getRol_id() == 3 && estadoActual.getEstado_id() < 3) {
            estadoAsignado = estadoRepository.findById(3L)
                    .orElseThrow(() -> new RuntimeException("Estado 3 no encontrado"));
        } else if (usuario.getRol().getRol_id() == 3 && estadoActual.getEstado_id() >= 3) {
            estadoAsignado = estadoActual;
        } else {
            throw new RuntimeException("El rol del usuario no es válido para asignación.");
        }

        // Actualizar el seguimiento del día
        seguimientoHoy.setEstado(estadoAsignado);

        // CREAR nueva asignación (historial)
        OrdenUsuario asignacion = new OrdenUsuario();
        asignacion.setOrden(orden);
        asignacion.setUsuario(usuario);
        asignacion.setFecha_asignacion(LocalDate.now());

        // Relación bidireccional
        orden.getOrdenUsuarios().add(asignacion);

        return ordenRepository.save(orden);
    }
}
