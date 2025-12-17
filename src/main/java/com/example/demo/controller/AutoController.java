package com.example.demo.controller;

import com.example.demo.dto.CondicionAutoDTO;
import com.example.demo.model.Condicion_auto;
import com.example.demo.service.AutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/autos")
@RequiredArgsConstructor
public class AutoController {

    @Autowired
    private AutoService autoService;

    @PostMapping("/condicion/registrar")
    public ResponseEntity<?> registrarCondicion(@RequestBody CondicionAutoDTO dto) {
        Map<String, Object> response = new HashMap<>();

        if (dto.getAuto_id() == null) {
            response.put("data", Map.of("error", "El id del auto es obligatorio."));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (dto.getDescripcion() == null || dto.getDescripcion().trim().isEmpty()) {
            response.put("data", Map.of("error", "La descripción es obligatoria."));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (dto.getObservaciones() == null || dto.getObservaciones().trim().isEmpty()) {
            response.put("data", Map.of("error", "Las observaciones son obligatorias."));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }


        if (dto.getKilometraje() == null) {
            response.put("data", Map.of("error", "El kilometraje es obligatorio."));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }


        if (dto.getDetalles() == null || dto.getDetalles().isEmpty()) {
            response.put("data", Map.of("error", "Las condiciones del auto son obligatorias."));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Condicion_auto guardado = autoService.registrarCondicion(dto);

        return ResponseEntity.ok(Map.of(
                "message", "Condición registrada correctamente",
                "data", guardado
        ));
    }

    @GetMapping("/condicion/actual")
    public ResponseEntity<?> obtenerCondicionesActuales(@RequestParam List<Long> ids) {
        List<Condicion_auto> condiciones = autoService.obtenerEstadosActuales(ids);

        return ResponseEntity.ok(Map.of("data", condiciones));
    }

    @PatchMapping("/condiciones/auto/{autoId}")
    public ResponseEntity<?> actualizarCondicion(
            @PathVariable Long autoId,
            @RequestBody Map<String, Object> detalles
    ) {
        return ResponseEntity.ok(
                autoService.actualizarCondicion(autoId, detalles)
        );
    }
}
