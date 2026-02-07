package com.example.demo.service;

import com.example.demo.dto.PiezaAccionDTO;
import com.example.demo.model.Pieza;
import com.example.demo.repo.PiezaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PiezaService {

    private final PiezaRepository piezaRepository;


    public List<Pieza> listarPiezas() {
        return piezaRepository.findAll();
    }


    public Object ejecutarAccion(PiezaAccionDTO dto) {

        if (dto.getAccion() == null || dto.getAccion().trim().isEmpty()) {
            throw new RuntimeException("La acción es obligatoria (AGREGAR o ELIMINAR).");
        }

        String accion = dto.getAccion().trim().toUpperCase();

        switch (accion) {

            case "AGREGAR" -> {

                if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
                    throw new RuntimeException("El nombre de la pieza es obligatorio.");
                }

                if (dto.getCosto_unitario() == null) {
                    throw new RuntimeException("El costo_unitario es obligatorio.");
                }

                // impedir costos negativos
                if (dto.getCosto_unitario() < 0) {
                    throw new RuntimeException("El costo_unitario no puede ser negativo.");
                }

                Pieza nueva = Pieza.builder()
                        .nombre(dto.getNombre().trim())
                        .costo_unitario(dto.getCosto_unitario())
                        .build();

                return piezaRepository.save(nueva);
            }

            case "ELIMINAR" -> {

                if (dto.getPieza_id() == null) {
                    throw new RuntimeException("El pieza_id es obligatorio para eliminar.");
                }

                Pieza pieza = piezaRepository.findById(Long.valueOf(dto.getPieza_id()))
                        .orElseThrow(() -> new RuntimeException("Pieza no encontrada."));

                piezaRepository.delete(pieza);

                return dto.getPieza_id();
            }

            default -> throw new RuntimeException("Acción inválida. Usa AGREGAR o ELIMINAR.");
        }
    }
}
