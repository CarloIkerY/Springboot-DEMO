package com.example.demo.service;

import com.example.demo.dto.OrdenDTO;
import com.example.demo.model.Auto;
import com.example.demo.model.Estado;
import com.example.demo.model.Orden;
import com.example.demo.model.Seguimiento;
import com.example.demo.repo.AutoRepository;
import com.example.demo.repo.EstadoRepository;
import com.example.demo.repo.OrdenRepository;
import com.example.demo.repo.SeguimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenService {
    private final AutoRepository autoRepository;
    private final OrdenRepository ordenRepository;
    private final SeguimientoRepository seguimientoRepository;
    private final EstadoRepository estadoRepository;

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
}
