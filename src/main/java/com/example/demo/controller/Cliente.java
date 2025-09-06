package com.example.demo.controller;

import com.example.demo.dto.AutoDTO;
import com.example.demo.dto.ClienteConAutoDTO;
import com.example.demo.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class Cliente {

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

    @GetMapping("/buscar/placa")
    public ResponseEntity<?> buscarClientesPorPlaca(@RequestParam String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La placa no puede estar vacía.");
        }

        List<ClienteConAutoDTO> clientes = clienteService.findClientesByPlaca(placa);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/buscar/telefono")
    public ResponseEntity<?> buscarClientesPorTelefono(@RequestParam String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El número de teléfono no puede estar vacío.");
        }

        List<ClienteConAutoDTO> clientes = clienteService.findClientesByTelefono(telefono);
        return ResponseEntity.ok(clientes);
    }
    
}