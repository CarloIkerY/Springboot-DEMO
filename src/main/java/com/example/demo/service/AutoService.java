package com.example.demo.service;

import com.example.demo.dto.CondicionAutoDTO;
import com.example.demo.model.Auto;
import com.example.demo.model.Condicion_auto;
import com.example.demo.repo.AutoRepository;
import com.example.demo.repo.CondicionAutoRepository;
import com.example.demo.utils.CondicionAutoTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AutoService {

    @Autowired
    private AutoRepository autoRepository;

    @Autowired
    private CondicionAutoRepository condicionAutoRepository;

    public Condicion_auto registrarCondicion(CondicionAutoDTO dto) {

        Auto auto = autoRepository.findById(dto.getAuto_id())
                .orElseThrow(() -> new RuntimeException("Auto no encontrado"));

        // 1. Crear condición
        Condicion_auto condicion = new Condicion_auto();
        condicion.setAuto(auto);
        condicion.setFecha_origen(LocalDateTime.now());
        condicion.setDescripcion(dto.getDescripcion());
        condicion.setObservaciones(dto.getObservaciones());
        condicion.setKilometraje(dto.getKilometraje());
        condicion.setEstado_actual(true);

        // 2. Cargar plantilla base (YA ES LinkedHashMap)
        LinkedHashMap<String, Object> plantilla = CondicionAutoTemplate.getTemplate();

        // 3. Si el usuario envía detalles, aseguramos que mantengan orden
        if (dto.getDetalles() != null) {
            // Convertir los detalles entrantes a LinkedHashMap para mantener orden
            LinkedHashMap<String, Object> detallesOrdenados = new LinkedHashMap<>(dto.getDetalles());

            // Mezclar plantilla + valores del usuario
            for (String key : detallesOrdenados.keySet()) {
                plantilla.put(key, detallesOrdenados.get(key));
            }
        }

        condicion.setDetalles(plantilla);

        return condicionAutoRepository.save(condicion);
    }
}