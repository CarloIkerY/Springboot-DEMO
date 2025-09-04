package com.example.demo.controller;

import com.example.demo.dto.AutoDTO;
import com.example.demo.dto.ClienteConAutoDTO;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class RegistrarCliente {

    private final ClienteService clienteService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarClienteConAuto(@RequestBody ClienteConAutoDTO dto) {

        // Validación manual
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre es obligatorio.");
        }

        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El email es obligatorio.");
        }

        // ✅ Validación de la lista de autos
        if (dto.getAutos() == null || dto.getAutos().isEmpty()) {
            return ResponseEntity.badRequest().body("Debe incluir al menos un auto.");
        }

        AutoDTO auto = dto.getAutos().get(0); // tomamos el primero por ahora

        if (auto.getMarca() == null || auto.getMarca().trim().isEmpty() ||
                auto.getModelo() == null || auto.getModelo().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Los datos del auto son obligatorios.");
        }

        ClienteConAutoDTO clienteCreado = clienteService.createClienteConAuto(dto);
        return ResponseEntity.ok(clienteCreado);
    }
}