package com.example.demo.controller;

import com.example.demo.dto.PiezaAccionDTO;
import com.example.demo.model.Pieza;
import com.example.demo.service.PiezaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pieza")
@RequiredArgsConstructor
public class PiezaController {

    private final PiezaService piezaService;


    @GetMapping("/listado")
    public ResponseEntity<?> obtenerPiezas() {

        List<Pieza> piezas = piezaService.listarPiezas();

        return ResponseEntity.ok(Map.of(
                "data", piezas,
                "message", "Lista de piezas obtenida correctamente"
        ));
    }

    // AGREGAR / ELIMINAR
    @PostMapping("/accion")
    public ResponseEntity<?> accionPieza(@RequestBody PiezaAccionDTO dto) {

        Map<String, Object> response = new HashMap<>();

        try {
            Object resultado = piezaService.ejecutarAccion(dto);

            response.put("success", true);

            if ("AGREGAR".equalsIgnoreCase(dto.getAccion())) {
                response.put("message", "Pieza agregada correctamente.");
                response.put("data", resultado);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }

            if ("ELIMINAR".equalsIgnoreCase(dto.getAccion())) {
                response.put("message", "Pieza eliminada correctamente.");
                response.put("data", Map.of("pieza_id", resultado));
                return ResponseEntity.ok(response);
            }

            response.put("message", "Acción ejecutada.");
            response.put("data", resultado);
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
