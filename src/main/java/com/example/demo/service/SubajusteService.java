package com.example.demo.service;

import com.example.demo.dto.response.OrdenResponseDTO;
import com.example.demo.dto.response.SubajusteDTO;
import com.example.demo.mappers.OrdenMapper;
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
public class SubajusteService {

    private final OrdenRepository ordenRepository;
    private final SubajusteRepository subajusteRepository;
    private final AjusteAutoRepository ajusteAutoRepository;
    private final OrdenMapper ordenMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public OrdenResponseDTO crearSubajustes(
            Long ordenId,
            Long ajusteAutoId,
            List<SubajusteDTO> subajustes
    ) {

        Ajuste_auto ajusteAuto = ajusteAutoRepository
                .findById(ajusteAutoId)
                .orElseThrow(() -> new RuntimeException("Ajuste auto no encontrado"));

        if (!ajusteAuto.getSeguimiento()
                .getOrden()
                .getOrden_id()
                .equals(ordenId)) {
            throw new RuntimeException("El ajuste no pertenece a la orden indicada");
        }

        for (SubajusteDTO dto : subajustes) {

            boolean existe = subajusteRepository
                    .findByAjusteAutoAndDescripcionIgnoreCase(
                            ajusteAuto,
                            dto.getDescripcion()
                    )
                    .isPresent();

            if (!existe) {
                Subajuste nuevo = new Subajuste();
                nuevo.setDescripcion(dto.getDescripcion());
                nuevo.setAjusteAuto(ajusteAuto);
                nuevo.setFecha_registro(LocalDateTime.now());


                subajusteRepository.save(nuevo);
            }
        }

        entityManager.flush();
        entityManager.clear();

        Orden orden = ordenRepository
                .findById(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        return ordenMapper.toDto(orden);
    }
}
