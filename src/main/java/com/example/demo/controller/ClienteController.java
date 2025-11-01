package com.example.demo.controller;

import com.example.demo.dto.AutoDTO;
import com.example.demo.dto.ClienteConAutoDTO;
import com.example.demo.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/registrar")
    public ResponseEntity<Map<String, Object>> registrarClienteConAuto(@RequestBody ClienteConAutoDTO dto) {
        Map<String, Object> response = new HashMap<>();

        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            response.put("data", Map.of("error", "El nombre es obligatorio."));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (dto.getCelular() == null || dto.getCelular().trim().isEmpty()) {
            response.put("data", Map.of("error", "El celular es obligatorio."));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (dto.getDireccion() == null || dto.getDireccion().trim().isEmpty()) {
            response.put("data", Map.of("error", "La dirección es obligatoria."));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (dto.getAutos() == null || dto.getAutos().isEmpty()) {
            response.put("data", Map.of("error", "Debe incluir al menos un auto."));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        AutoDTO auto = dto.getAutos().get(0);
        if (auto.getMarca() == null || auto.getMarca().trim().isEmpty() ||
                auto.getModelo() == null || auto.getModelo().trim().isEmpty()) {
            response.put("data", Map.of("error", "Los datos del auto son obligatorios."));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ClienteConAutoDTO clienteCreado = clienteService.createClienteConAuto(dto);
        response.put("data", clienteCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/buscarClientes")
    public ResponseEntity<?> buscarClientesPorParametro(@RequestParam String parametro) {
        Map<String, Object> response = new HashMap<>();

        if (parametro == null || parametro.trim().isEmpty()) {
            response.put("data", List.of());
            response.put("message", "El parámetro es obligatorio.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Buscar en cada criterio
        List<ClienteConAutoDTO> clientesPlaca = clienteService.findClientesByPlaca(parametro);
        List<ClienteConAutoDTO> clientesModeloAuto = clienteService.findClientesByModeloAuto(parametro);
        List<ClienteConAutoDTO> clientesNombre = clienteService.findClientesByNombre(parametro);
        List<ClienteConAutoDTO> clientesByCelular = clienteService.findClientesByCelular(parametro);

        // Tomar la primera lista que no esté vacía
        List<ClienteConAutoDTO> resultado = Stream.of(clientesPlaca, clientesModeloAuto, clientesNombre, clientesByCelular)
                .filter(list -> !list.isEmpty())
                .findFirst()
                .orElse(List.of());

        response.put("data", resultado);
        if (resultado.isEmpty()) {
            response.put("message", "No se encontraron resultados");
        }

        return ResponseEntity.ok(response);
    }
}