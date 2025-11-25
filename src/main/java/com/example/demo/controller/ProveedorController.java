package com.example.demo.controller;

import com.example.demo.model.Proveedor;
import com.example.demo.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/proveedor")
@RequiredArgsConstructor
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/listado")
    public ResponseEntity<?> obtenerProveedores() {
        List<Proveedor> proveedores = proveedorService.listarProveedores();

        return ResponseEntity.ok(Map.of(
                "data", proveedores,
                "message", "Lista de proveedores obtenida correctamente"
        ));
    }
}
