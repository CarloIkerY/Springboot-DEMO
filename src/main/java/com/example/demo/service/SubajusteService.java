package com.example.demo.service;

import com.example.demo.dto.response.OrdenResponseDTO;
import com.example.demo.dto.response.SubajusteDTO;
import com.example.demo.mappers.OrdenMapper;
import com.example.demo.model.Actividad;
import com.example.demo.model.Ajuste_auto;
import com.example.demo.model.Orden;
import com.example.demo.model.Subajuste;
import com.example.demo.repo.ActividadRespository;
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
    private final ActividadRespository actividadRespository;
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
        //entityManager.clear();

        Orden orden = ordenRepository
                .findById(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        return ordenMapper.toDto(orden);
    }

    @Transactional
    public OrdenResponseDTO finalizarSubajustes(
            Long orden_id,
            Long ajusteAuto_id,
            List<Long> subajustesId
    ) {
        for(Long subajusteId: subajustesId){
            Subajuste subajuste = subajusteRepository.findById(subajusteId)
                    .orElseThrow(() -> new RuntimeException("Subajuste no encontrada"));

            if (!subajuste.getAjusteAuto().getAjusteAuto_id().equals(ajusteAuto_id)) {
                throw new RuntimeException("El subajuste no pertenece al ajuste indicado");
            }

            List<Actividad> actividades =
                    actividadRespository.findAllBySubajusteId(subajusteId);

            if (actividades.isEmpty()) {
                throw new RuntimeException("El subajuste no tiene actividades");
            }

            boolean todasTerminadas = actividades.stream()
                    .allMatch(a -> Boolean.TRUE.equals(a.getTerminado()));

            if (!todasTerminadas) {
                throw new RuntimeException(
                        "No se puede finalizar el subajuste " + subajusteId +
                                " porque aún hay actividades pendientes"
                );
            }

            subajuste.setTerminado(true);
            subajusteRepository.save(subajuste);
        }

        entityManager.flush();
        entityManager.clear();

        Orden orden = ordenRepository.findByIdWithAllRelations(orden_id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        return ordenMapper.toDto(orden);
    }
}
