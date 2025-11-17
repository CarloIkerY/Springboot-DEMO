package com.example.demo.controller;

import com.example.demo.dto.OrdenDTO;
import com.example.demo.model.Orden;
import com.example.demo.service.OrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordenes")
@RequiredArgsConstructor
public class OrdenController {
    private final OrdenService ordenService;

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody OrdenDTO dto) {

        Map<String, Object> response = new HashMap<>();

        if (dto.getAuto_id() == null) {
            response.put("data", null);
            response.put("message", "El auto es obligatorio.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (dto.getEstado() == null) {
            response.put("data", null);
            response.put("message", "El estado es obligatorio.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            Orden orden = ordenService.crearOrden(dto);

            response.put("data", orden);
            response.put("message", "Orden creada exitosamente.");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("data", null);
            response.put("message", "Falló la creación de la orden.");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> filtrarPorEstados(@RequestParam String estados) {

        List<Long> lista = Arrays.stream(estados.split(","))
                .map(String::trim)
                .map(Long::valueOf)
                .toList();

        List<Orden> ordenes = ordenService.getOrdenesPorEstados(lista);

        Map<String, Object> response = new HashMap<>();
        response.put("data", ordenes);

        return ResponseEntity.ok(response);
    }
}
