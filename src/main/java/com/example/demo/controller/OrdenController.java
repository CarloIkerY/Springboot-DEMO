package com.example.demo.controller;

import com.example.demo.dto.OrdenDTO;
import com.example.demo.dto.request.FinalizarOrdenRequestDTO;
import com.example.demo.model.Orden;
import com.example.demo.service.OrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordenes")
@RequiredArgsConstructor
public class OrdenController {

    private final OrdenService ordenService;

    // Helpers para estandarizar respuestas
    private Map<String, Object> ok(Object data, String message) {
        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("data", data);
        res.put("message", message);
        return res;
    }

    private Map<String, Object> fail(String message) {
        Map<String, Object> res = new HashMap<>();
        res.put("success", false);
        res.put("data", null);
        res.put("message", message);
        return res;
    }

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
            e.printStackTrace();
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

    @PostMapping("/asignar")
    public ResponseEntity<?> asignar(@RequestBody OrdenDTO dto) {

        if (dto.getOrden_id() == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", "El ID de la orden es obligatorio.")
            );
        }

        if (dto.getUsuario_id() == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", "El ID del usuario es obligatorio.")
            );
        }

        try {
            Orden ordenActualizada = ordenService.asignarOrden(dto);

            return ResponseEntity.ok(Map.of(
                    "message", "Orden asignada correctamente",
                    "data", ordenActualizada
            ));

        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", ex.getMessage()));
        }
    }

    @PostMapping("/agendarRecoleccion")
    public ResponseEntity<?> agendarRecoleccion(@RequestBody OrdenDTO dto) {

        if (dto.getOrden_id() == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "El ID de Orden es obligatorio."
            ));
        }



        if (dto.getFecha_recoleccion() == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "La fecha para recolección es obligatoria."
            ));
        }

        try {
            Orden orden = ordenService.agendarFechaRecoleccion(dto);
            return ResponseEntity.ok(Map.of(
                    "message", "Fecha de recolección agendada correctamente",
                    "data", orden
            ));

        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Map.of(
                    "message", e.getReason()
            ));
        }
    }

    @PostMapping("/cambiarEstado")
    public ResponseEntity<?> cambiarEstado(@RequestBody OrdenDTO dto) {

        if (dto.getOrden_id() == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "El ID de la orden es obligatorio."
            ));
        }

        if (dto.getUsuario_id() == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "El ID del usuario es obligatorio."
            ));
        }

        if (dto.getEstado() == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "El estado es obligatorio."
            ));
        }

        try {
            Orden orden = ordenService.cambiarEstado(dto);

            return ResponseEntity.ok(Map.of(
                    "message", "Estado cambiado correctamente",
                    "data", orden
            ));

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/chofer/{choferId}")
    public ResponseEntity<?> obtenerOrdenesAsignadasAChofer(@PathVariable Long choferId) {

        List<Orden> ordenes = ordenService.obtenerOrdenesAsignadasAChofer(choferId);

        return ResponseEntity.ok(Map.of(
                "data", ordenes,
                "message", "Órdenes asignadas al chofer obtenidas correctamente"
        ));
    }

    @GetMapping("/mecanico/{mecanicoId}")
    public ResponseEntity<?> obtenerOrdenesAsignadasAMecanico(@PathVariable Long mecanicoId) {

        List<Orden> ordenes = ordenService.obtenerOrdenesAsignadasAMecanico(mecanicoId);

        return ResponseEntity.ok(Map.of(
                "data", ordenes,
                "message", "Órdenes asignadas al mecánico obtenidas correctamente"
        ));
    }

    // Finalizar Orden (entrega al cliente)
    @PatchMapping("/{ordenId}/finalizar")
    public ResponseEntity<?> finalizarOrden(
            @PathVariable Long ordenId,
            @RequestBody FinalizarOrdenRequestDTO body
    ) {
        if (body == null || body.getUsuarioId() == null) {
            return ResponseEntity.badRequest().body(
                    fail("El usuarioId es obligatorio para finalizar la orden.")
            );
        }

        try {
            Orden ordenFinalizada = ordenService.finalizarOrden(
                    ordenId,
                    body.getUsuarioId(),
                    body.getFechaEntrega()
            );

            return ResponseEntity.ok(
                    ok(ordenFinalizada, "Orden finalizada correctamente")
            );

        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(fail(e.getReason()));
        }
    }
}