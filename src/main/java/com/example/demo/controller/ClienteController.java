package com.example.demo.controller;

import com.example.demo.config.ApiResponse;
import com.example.demo.dto.AutoDTO;
import com.example.demo.dto.ClienteConAutoDTO;
import com.example.demo.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/registrar")
    public ResponseEntity<ApiResponse<ClienteConAutoDTO>> registrarClienteConAuto(@RequestBody ClienteConAutoDTO dto) {

        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<ClienteConAutoDTO>builder()
                            .statusCode(400)
                            .message("El nombre es obligatorio.")
                            .data(null)
                            .build()
            );
        }

        if (dto.getCelular() == null || dto.getCelular().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<ClienteConAutoDTO>builder()
                            .statusCode(400)
                            .message("El celular es obligatorio.")
                            .data(null)
                            .build()
            );
        }

        if (dto.getDireccion() == null || dto.getDireccion().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<ClienteConAutoDTO>builder()
                            .statusCode(400)
                            .message("La dirección es obligatoria.")
                            .data(null)
                            .build()
            );
        }

        if (dto.getAutos() == null || dto.getAutos().isEmpty()) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<ClienteConAutoDTO>builder()
                            .statusCode(400)
                            .message("Debe incluir al menos un auto.")
                            .data(null)
                            .build()
            );
        }

        AutoDTO auto = dto.getAutos().get(0);
        if (auto.getMarca() == null || auto.getMarca().trim().isEmpty() ||
                auto.getModelo() == null || auto.getModelo().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<ClienteConAutoDTO>builder()
                            .statusCode(400)
                            .message("Los datos del auto son obligatorios.")
                            .data(null)
                            .build()
            );
        }

        ClienteConAutoDTO clienteCreado = clienteService.createClienteConAuto(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<ClienteConAutoDTO>builder()
                        .statusCode(201)
                        .message("Cliente registrado correctamente.")
                        .data(clienteCreado)
                        .build()
        );
    }

    @GetMapping("/buscar/placa")
    public ResponseEntity<?> buscarClientesPorPlaca(@RequestParam String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La placa no puede estar vacía.");
        }

        List<ClienteConAutoDTO> clientes = clienteService.findClientesByPlaca(placa);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/buscar/nombre")
    public ResponseEntity<?> buscarClientesPorNombre(@RequestParam String nombre) {
        if(nombre == null || nombre.trim().isEmpty()){
            return ResponseEntity.badRequest().body("El nombre es obligatorio.");
        }

        List<ClienteConAutoDTO> clientes = clienteService.findClientesByNombre(nombre);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/buscar/email")
    public ResponseEntity<?> buscarClientesPorEmail(@RequestParam String correo) {
        if(correo == null || correo.trim().isEmpty()){
            return ResponseEntity.badRequest().body("El email es obligatorio.");
        }

        List<ClienteConAutoDTO> clientes = clienteService.findClientesByCorreo(correo);
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