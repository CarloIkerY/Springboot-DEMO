package com.example.demo.controller;

import com.example.demo.dto.request.AceptarAjustesRequestDTO;
import com.example.demo.dto.request.AjusteAutoRequestDTO;
import com.example.demo.dto.request.AjusteAutoTerminadoDTO;
import com.example.demo.dto.request.AjusteAutoUpdateRequestDTO;
import com.example.demo.dto.response.AjusteAutoResponseDTO;
import com.example.demo.dto.response.OrdenResponseDTO;
import com.example.demo.service.AjusteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ajustes")
@RequiredArgsConstructor
public class AjusteController {

    private final AjusteService ajusteService;

    @PostMapping("/crearAjustesAuto")
    public ResponseEntity<?> crear(@RequestBody AjusteAutoRequestDTO dto) {

        Map<String, Object> response = new HashMap<>();

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

        OrdenResponseDTO result =
                ajusteService.crearAjustes(dto.getOrden_id(), dto.getAjustes());

        response.put("message", "Ajustes creados correctamente.");
        response.put("data", result);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/ajustesAprobacion")
    public ResponseEntity<?> obtenerOrdenesEstado7() {

        Map<String, Object> response = new HashMap<>();

        List<OrdenResponseDTO> ordenes =
                ajusteService.obtenerAjustesPendientes();

        response.put("message", "Órdenes con ajustes pendientes obtenidas correctamente");
        response.put("data", ordenes);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/actualizarAjusteAuto")
    public ResponseEntity<?> actualizarAjuste(@RequestBody AjusteAutoUpdateRequestDTO dto) {

        Map<String, Object> response = new HashMap<>();

        if (dto.getAjusteAuto_id() == null) {
            response.put("message", "El ajusteAuto_id es obligatorio.");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (dto.getDescripcion() == null || dto.getDescripcion().isBlank()) {
            response.put("message", "La descripción es obligatoria.");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }

        AjusteAutoResponseDTO result =
                ajusteService.actualizarAjuste(dto);

        response.put("message", "Ajuste actualizado correctamente.");
        response.put("data", result);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/aceptarAjustes")
    public ResponseEntity<?> aceptarAjustes(
            @RequestBody AceptarAjustesRequestDTO dto) {

        Map<String, Object> response = new HashMap<>();

        if (dto.getOrden_id() == null) {
            response.put("message", "La orden es obligatoria.");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (dto.getAjustes_aceptados() == null) {
            response.put("message", "Debe indicar si los ajustes fueron aceptados.");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }

        // Llamar al servicio
        OrdenResponseDTO result = ajusteService.aceptarAjustes(dto);

        response.put("message", "Estado de ajustes actualizado correctamente.");
        response.put("data", result);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/terminarActividades")
    public ResponseEntity<?> terminarActividades(@RequestBody AjusteAutoTerminadoDTO dto) {
        Map<String, Object> response = new HashMap<>();

        if(dto.getOrden_id() == null) {
            response.put("message", "La orden_id es obligatoria.");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }

        if(dto.getAjustesAuto_id() == null) {
            response.put("message", "Los ajustesAuto_id son obligatorios.");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }

        OrdenResponseDTO result = ajusteService.finalizarAjustes(
                dto.getOrden_id(),
                dto.getAjustesAuto_id()
        );

        response.put("message", "AjustesAuto finalizado correctamente.");
        response.put("data", result);

        return ResponseEntity.ok(response);
    }
}
