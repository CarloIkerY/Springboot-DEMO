package com.example.demo.controller;

import com.example.demo.dto.AjusteAutoDTO;
import com.example.demo.dto.AjusteDTO;
import com.example.demo.dto.OrdenDTO;
import com.example.demo.model.Ajuste_auto;
import com.example.demo.service.AjusteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ajustes")
@RequiredArgsConstructor
public class AjusteController {

    private final AjusteService ajusteService;

    @PostMapping("/crearAjustesAuto")
    public ResponseEntity<?> crear(@RequestBody AjusteAutoDTO dto) {

        Map<String, Object> response = new HashMap<>();

        // 1️⃣ Validaciones
        if (dto.getOrden_id() == null) {
            response.put("message", "La orden es obligatoria.");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (dto.getAjustes() == null || dto.getAjustes().isEmpty()) {
            response.put("message", "La lista de ajustes no puede estar vacía.");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }

        for (AjusteDTO ajuste : dto.getAjustes()) {
            if (ajuste.getDescripcion() == null || ajuste.getDescripcion().isBlank()) {
                response.put("message", "Todos los ajustes deben tener descripción.");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
        }

        // 2️⃣ Llamar al servicio
        List<Ajuste_auto> result = ajusteService.crearAjustes(dto.getOrden_id(), dto.getAjustes());

        // 3️⃣ Respuesta
        response.put("message", "Ajustes creados correctamente.");
        response.put("data", result);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
