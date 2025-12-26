package com.example.demo.service;

import com.example.demo.dto.AjusteAutoDTO;
import com.example.demo.dto.AjusteDTO;
import com.example.demo.model.Ajuste;
import com.example.demo.model.Ajuste_auto;
import com.example.demo.model.Seguimiento;
import com.example.demo.repo.AjusteAutoRepository;
import com.example.demo.repo.AjusteRepository;
import com.example.demo.repo.SeguimientoRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public List<Ajuste_auto> crearAjustes(Long ordenId, List<AjusteDTO> ajustes) {

        Seguimiento seguimiento = seguimientoRepository
                .findByOrdenId(ordenId)
                .orElseThrow(() -> new RuntimeException("Seguimiento no encontrado"));

        List<Ajuste_auto> creados = new ArrayList<>();

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

            creados.add(ajusteAutoRepository.save(ajusteAuto));
        }

        return creados;
    }
}