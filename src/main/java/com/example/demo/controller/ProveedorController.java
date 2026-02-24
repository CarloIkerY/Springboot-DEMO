package com.example.demo.controller;

import com.example.demo.dto.ProveedorDTO;
import com.example.demo.model.Proveedor;
import com.example.demo.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/proveedor")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @GetMapping("/listado")
    public ResponseEntity<?> obtenerProveedores() {
        List<Proveedor> proveedores = proveedorService.listarProveedores();

        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", proveedores,
                "message", "Lista de proveedores obtenida correctamente"
        ));
    }

    @PostMapping("/accion")
    public ResponseEntity<?> accionProveedor(@RequestBody ProveedorDTO dto) {

        Map<String, Object> response = new HashMap<>();

        try {
            Object resultado = proveedorService.ejecutarAccion(dto);

            response.put("success", true);

            if ("AGREGAR".equalsIgnoreCase(dto.getAccion())) {
                response.put("message", "Proveedor agregado correctamente.");
                response.put("data", resultado);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }

            if ("ELIMINAR".equalsIgnoreCase(dto.getAccion())) {
                response.put("message", "Proveedor eliminado correctamente.");
                response.put("data", Map.of("proveedor_id", resultado));
                return ResponseEntity.ok(response);
            }

            response.put("message", "Acción ejecutada.");
            response.put("data", resultado);
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }
    }
}