package com.example.demo.controller;

import com.example.demo.config.ApiResponse;
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

    @GetMapping("/buscar/placa")
    public ResponseEntity<?> buscarClientesPorPlaca(@RequestParam String placa) {
        Map<String, Object> response = new HashMap<>();

        if (placa == null || placa.trim().isEmpty()) {
            response.put("data", Map.of("error", "El placa es obligatorio."));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        List<ClienteConAutoDTO> clientes = clienteService.findClientesByPlaca(placa);
        response.put("data", clientes);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/buscar/modeloAuto")
    public ResponseEntity<?> buscarClientesPorModeloAuto(@RequestParam String modelo) {
        Map<String, Object> response = new HashMap<>();

        if (modelo == null || modelo.trim().isEmpty()) {
            response.put("data", Map.of("error", "El modelo es obligatorio."));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        List<ClienteConAutoDTO> clientes = clienteService.findClientesByModeloAuto(modelo);
        response.put("data", clientes);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/buscar/nombre")
    public ResponseEntity<?> buscarClientesPorNombre(@RequestParam String nombre) {
        Map<String, Object> response = new HashMap<>();

        if(nombre == null || nombre.trim().isEmpty()){
            response.put("data", Map.of("error", "El Nombre es obligatorio."));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        List<ClienteConAutoDTO> clientes = clienteService.findClientesByNombre(nombre);
        response.put("data", clientes);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/buscar/celular")
    public ResponseEntity<?> buscarClientesPorCelular(@RequestParam String celular) {
        Map<String, Object> response = new HashMap<>();

        if (celular == null || celular.trim().isEmpty()) {
            response.put("data", Map.of("error", "El Celular es obligatorio."));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        List<ClienteConAutoDTO> clientes = clienteService.findClientesByCelular(celular);
        response.put("data", clientes);
        return ResponseEntity.ok(clientes);
    }
}