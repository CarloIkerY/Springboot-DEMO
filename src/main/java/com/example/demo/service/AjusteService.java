package com.example.demo.service;

import com.example.demo.dto.AjusteDTO;
import com.example.demo.dto.request.AjusteAutoUpdateRequestDTO;
import com.example.demo.dto.response.AjusteAutoResponseDTO;
import com.example.demo.dto.response.OrdenResponseDTO;
import com.example.demo.mappers.AjusteAutoMapper;
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
    private final AjusteAutoMapper ajusteAutoMapper;

    @Transactional
    public OrdenResponseDTO crearAjustes(Long ordenId, List<AjusteDTO> ajustes) {

        Seguimiento seguimiento = seguimientoRepository
                .findByOrdenId(ordenId)
                .orElseThrow(() -> new RuntimeException("Seguimiento no encontrado"));

        Estado estado7 = estadoRepository.findById(7L)
                .orElseThrow(() -> new RuntimeException("Estado 7 no encontrado"));

        seguimiento.setEstado(estado7);
        seguimiento.setFecha_actualizacion(LocalDateTime.now());

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

    @Transactional
    public AjusteAutoResponseDTO actualizarAjuste(AjusteAutoUpdateRequestDTO dto) {

        Ajuste_auto ajusteAuto = ajusteAutoRepository
                .findById(dto.getAjusteAuto_id())
                .orElseThrow(() -> new RuntimeException("Ajuste no encontrado"));

        Seguimiento seguimiento = ajusteAuto.getSeguimiento();

        if (!seguimiento.getOrden().getOrden_id().equals(dto.getOrden_id())) {
            throw new RuntimeException("El ajuste no pertenece a la orden indicada");
        }

        if (Boolean.TRUE.equals(seguimiento.getAjustes_aceptados())) {
            throw new RuntimeException(
                    "No se puede modificar el ajuste porque ya fue aceptado"
            );
        }

        Ajuste ajuste = ajusteRepository
                .findByDescripcionIgnoreCase(dto.getDescripcion())
                .orElseGet(() -> {
                    Ajuste nuevo = new Ajuste();
                    nuevo.setDescripcion(dto.getDescripcion());
                    return ajusteRepository.save(nuevo);
                });

        ajusteAuto.setAjuste(ajuste);

        ajusteAutoMapper.updateEntityFromDto(dto, ajusteAuto);

        Estado estado7 = estadoRepository.findById(7L)
                .orElseThrow(() -> new RuntimeException("Estado 7 no encontrado"));

        seguimiento.setEstado(estado7);
        seguimiento.setFecha_actualizacion(LocalDateTime.now());

        Ajuste_auto saved = ajusteAutoRepository.save(ajusteAuto);

        return ajusteAutoMapper.toDto(saved);
    }
}