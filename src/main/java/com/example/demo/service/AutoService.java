package com.example.demo.service;

import com.example.demo.dto.CondicionAutoDTO;
import com.example.demo.model.Auto;
import com.example.demo.model.Condicion_auto;
import com.example.demo.model.Estado;
import com.example.demo.model.Seguimiento;
import com.example.demo.repo.AutoRepository;
import com.example.demo.repo.CondicionAutoRepository;
import com.example.demo.repo.EstadoRepository;
import com.example.demo.repo.SeguimientoRepository;
import com.example.demo.utils.CondicionAutoTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AutoService {

    @Autowired
    private final AutoRepository autoRepository;
    private final CondicionAutoRepository condicionAutoRepository;
    private final SeguimientoRepository seguimientoRepository;
    private final EstadoRepository estadoRepository;

    public Condicion_auto registrarCondicion(CondicionAutoDTO dto) {

        Auto auto = autoRepository.findById(dto.getAuto_id())
                .orElseThrow(() -> new RuntimeException("Auto no encontrado"));

        // Crear condición
        Condicion_auto condicion = new Condicion_auto();
        condicion.setAuto(auto);
        condicion.setFecha_origen(LocalDateTime.now());
        condicion.setDescripcion(dto.getDescripcion());
        condicion.setObservaciones(dto.getObservaciones());
        condicion.setKilometraje(dto.getKilometraje());
        condicion.setEstado_actual(true);

        // Plantilla
        LinkedHashMap<String, Object> plantilla = CondicionAutoTemplate.getTemplate();

        if (dto.getDetalles() != null) {
            LinkedHashMap<String, Object> detallesOrdenados = new LinkedHashMap<>(dto.getDetalles());
            detallesOrdenados.forEach(plantilla::put);
        }

        condicion.setDetalles(plantilla);

        // 🔥 1. Obtener seguimiento desde orden
        Seguimiento seguimiento = seguimientoRepository.findByOrdenId(dto.getOrden_id())
                .orElseThrow(() -> new RuntimeException("Seguimiento no encontrado para la orden"));

        // 🔥 2. Cargar estado 4
        Estado estado = estadoRepository.findById(4L)
                .orElseThrow(() -> new RuntimeException("No existe estado 4"));

        // 🔥 3. Cambiar estado en seguimiento
        seguimiento.setEstado(estado);
        seguimientoRepository.save(seguimiento);

        return condicionAutoRepository.save(condicion);
    }

    public List<Condicion_auto> obtenerEstadosActuales(List<Long> ids) {
        return ids.stream()
                .map(autoId ->
                        condicionAutoRepository.findActiveByAutoId(autoId).orElse(null)
                )
                .toList();
    }
}