package com.example.demo.service;

import com.example.demo.dto.AjusteDTO;
import com.example.demo.dto.response.OrdenResponseDTO;
import com.example.demo.mappers.OrdenMapper;
import com.example.demo.model.*;
import com.example.demo.repo.*;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AjusteService {

    private final AjusteRepository ajusteRepository;
    private final AjusteAutoRepository ajusteAutoRepository;
    private final SeguimientoRepository seguimientoRepository;
    private final EstadoRepository estadoRepository;
    private final OrdenRepository ordenRepository;
    private final OrdenMapper ordenMapper;

    @Transactional
    public OrdenResponseDTO crearAjustes(Long ordenId, List<AjusteDTO> ajustes) {

        // 1️⃣ Obtener seguimiento
        Seguimiento seguimiento = seguimientoRepository
                .findByOrdenId(ordenId)
                .orElseThrow(() -> new RuntimeException("Seguimiento no encontrado"));

        // 2️⃣ Cambiar estado a 7
        Estado estado7 = estadoRepository.findById(7L)
                .orElseThrow(() -> new RuntimeException("Estado 7 no encontrado"));

        seguimiento.setEstado(estado7);
        seguimiento.setFecha_actualizacion(LocalDateTime.now());

        // 3️⃣ Crear ajustes
        for (AjusteDTO dto : ajustes) {

            Ajuste ajuste = ajusteRepository
                    .findByDescripcionIgnoreCase(dto.getDescripcion())
                    .orElseGet(() -> {
                        Ajuste nuevo = new Ajuste();
                        nuevo.setDescripcion(dto.getDescripcion());
                        return ajusteRepository.save(nuevo);
                    });

            Ajuste_auto ajusteAuto = new Ajuste_auto();
            ajusteAuto.setAjuste(ajuste);
            ajusteAuto.setSeguimiento(seguimiento);
            ajusteAuto.setDescripcion(dto.getDescripcion());
            ajusteAuto.setFecha_creacion(LocalDateTime.now());

            ajusteAutoRepository.save(ajusteAuto);
        }

        // 4️⃣ Obtener la orden completa y mapearla
        Orden orden = ordenRepository.findById(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        return ordenMapper.toDto(orden);
    }

    @Transactional(readOnly = true)
    public List<OrdenResponseDTO> obtenerAjustesPendientes() {
        return ordenRepository.findOrdenesConAjustesPorEstado(7L)
                .stream()
                .map(ordenMapper::toDto)
                .toList();
    }
}