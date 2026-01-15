package com.example.demo.service;

import com.example.demo.dto.response.ActividadDTO;
import com.example.demo.dto.response.OrdenResponseDTO;
import com.example.demo.mappers.OrdenMapper;
import com.example.demo.model.Actividad;
import com.example.demo.model.Ajuste_auto;
import com.example.demo.model.Orden;
import com.example.demo.model.Subajuste;
import com.example.demo.repo.AjusteAutoRepository;
import com.example.demo.repo.OrdenRepository;
import com.example.demo.repo.SubajusteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ActividadService {

    private final SubajusteRepository subajusteRepository;
    private final AjusteAutoRepository ajusteAutoRepository;
    private final OrdenRepository ordenRepository;
    private final OrdenMapper ordenMapper;
    @PersistenceContext
    private EntityManager entityManager;

    public OrdenResponseDTO agregarActividades(
            Long ordenId,
            Long ajusteAutoId,
            Long subajusteId,
            List<ActividadDTO> actividadesDto
    ) {

        // 1. Validar que exista el ajuste y pertenezca a la orden
        Ajuste_auto ajusteAuto = ajusteAutoRepository
                .findById(ajusteAutoId)
                .orElseThrow(() -> new RuntimeException("Ajuste auto no encontrado"));

        if (!ajusteAuto.getSeguimiento().getOrden().getOrden_id().equals(ordenId)) {
            throw new RuntimeException("El ajuste no pertenece a la orden indicada");
        }

        // 2. Validar que el subajuste exista y pertenezca al ajuste
        Subajuste subajuste = subajusteRepository
                .findById(subajusteId)
                .orElseThrow(() -> new RuntimeException("Subajuste no encontrado"));

        if (!subajuste.getAjusteAuto().getAjusteAuto_id().equals(ajusteAutoId)) {
            throw new RuntimeException("El subajuste no pertenece al ajuste indicado");
        }

        // 3. Agregar actividades
        for (ActividadDTO dto : actividadesDto) {
            Actividad actividad = new Actividad();
            actividad.setDescripcion(dto.getDescripcion());
            actividad.setFecha_registro(LocalDateTime.now());
            actividad.setSubajuste(subajuste);
            entityManager.persist(actividad);
            subajuste.getActividades().add(actividad);
        }

// 🔹 mapear directamente desde subajuste, evitando buscar la orden de nuevo
        Orden orden = ordenRepository.findByIdWithAllRelations(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        return ordenMapper.toDto(orden);
    }
}
