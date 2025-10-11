package com.example.demo.controller;

import com.example.demo.config.AESUtil;
import com.example.demo.dto.AutoDTO;
import com.example.demo.dto.ClienteConAutoDTO;
import com.example.demo.security.AuthorizationService;
import com.example.demo.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final AuthorizationService authorizationService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarClienteConAuto(@RequestBody ClienteConAutoDTO dto, @RequestParam Long usuarioId) {

        if (!authorizationService.tieneAcceso(usuarioId, 1L, 2L)) {
            return ResponseEntity.status(403).body("Acceso denegado: solo ADMIN o AGENTE pueden registrar clientes.");
        }

        // Validación manual
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre es obligatorio.");
        }

        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El email es obligatorio.");
        }

//        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
//            return ResponseEntity.badRequest().body("Seleccione una contraseña");
//        }

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
    public ResponseEntity<?> buscarClientesPorPlaca(@RequestParam String placa, @RequestParam Long usuarioId) {

        if (!authorizationService.tieneAcceso(usuarioId, 1L, 2L)) {
            return ResponseEntity.status(403).body("Acceso denegado: solo ADMIN o AGENTE pueden registrar clientes.");
        }

        if (placa == null || placa.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La placa no puede estar vacía.");
        }

        String placaEncriptado = AESUtil.encrypt(placa);
        List<ClienteConAutoDTO> clientes = clienteService.findClientesByPlaca(placaEncriptado);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/buscar/nombre")
    public ResponseEntity<?> buscarClientesPorNombre(@RequestParam String nombre, @RequestParam Long usuarioId) {

        if (!authorizationService.tieneAcceso(usuarioId, 1L, 2L)) {
            return ResponseEntity.status(403).body("Acceso denegado: solo ADMIN o AGENTE pueden registrar clientes.");
        }

        if(nombre == null || nombre.trim().isEmpty()){
            return ResponseEntity.badRequest().body("El nombre es obligatorio.");
        }

        String nombreEncriptado = AESUtil.encrypt(nombre);
        List<ClienteConAutoDTO> clientes = clienteService.findClientesByNombre(nombreEncriptado);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/buscar/email")
    public ResponseEntity<?> buscarClientesPorEmail(@RequestParam String email, @RequestParam Long usuarioId) {

        if (!authorizationService.tieneAcceso(usuarioId, 1L, 2L)) {
            return ResponseEntity.status(403).body("Acceso denegado: solo ADMIN o AGENTE pueden registrar clientes.");
        }

        if(email == null || email.trim().isEmpty()){
            return ResponseEntity.badRequest().body("El email es obligatorio.");
        }

        String emailEncriptado = AESUtil.encrypt(email);
        List<ClienteConAutoDTO> clientes = clienteService.findClientesByEmail(emailEncriptado);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/buscar/telefono")
    public ResponseEntity<?> buscarClientesPorTelefono(@RequestParam String telefono, @RequestParam Long usuarioId) {

        if (!authorizationService.tieneAcceso(usuarioId, 1L, 2L)) {
            return ResponseEntity.status(403).body("Acceso denegado: solo ADMIN o AGENTE pueden registrar clientes.");
        }

        if (telefono == null || telefono.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El número de teléfono no puede estar vacío.");
        }

        String telefonoEncriptado = AESUtil.encrypt(telefono);
        List<ClienteConAutoDTO> clientes = clienteService.findClientesByTelefono(telefonoEncriptado);
        return ResponseEntity.ok(clientes);
    }
}