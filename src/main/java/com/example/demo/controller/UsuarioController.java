package com.example.demo.controller;

import com.example.demo.config.AESUtil;
import com.example.demo.dto.UsuarioChoferDTO;
import com.example.demo.dto.UsuariosResponseDTO;
import com.example.demo.dto.VehiculoAsignado;
import com.example.demo.model.Usuario;
import com.example.demo.repo.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    // Método para desencriptar sin que truene el servidor
    private String safeDecrypt(String value) {
        if (value == null) return null;
        try {
            return AESUtil.decrypt(value);
        } catch (RuntimeException e) {

            return value;
        }
    }


    @GetMapping
    public ResponseEntity<UsuariosResponseDTO> obtenerUsuarios(
            @RequestParam String tipo,
            @RequestParam Boolean disponible
    ) {

        List<Usuario> usuarios = usuarioRepository
                .findByRol_NombreAndDisponible(tipo.toUpperCase(), disponible);

        List<UsuarioChoferDTO> data = usuarios.stream().map(u -> {
            String nombre = safeDecrypt(u.getNombre());
            String apellido = safeDecrypt(u.getApellido());
            String correo = safeDecrypt(u.getCorreo());
            String celular = safeDecrypt(u.getCelular());

            return UsuarioChoferDTO.builder()
                    .id(u.getUsuario_id())
                    .nombre((nombre != null ? nombre : "") + " " + (apellido != null ? apellido : ""))
                    .email(correo)
                    .telefono(celular) // ya tienes campo celular en Usuario
                    .tipo(u.getRol() != null ? u.getRol().getNombre().toLowerCase() : null)
                    .disponible(Boolean.TRUE.equals(u.getDisponible()))
                    .vehiculo_asignado((VehiculoAsignado) null) // por ahora sin vehículo
                    .build();
        }).toList();

        UsuariosResponseDTO respuesta = UsuariosResponseDTO.builder()
                .success(true)
                .data(data)
                .build();

        return ResponseEntity.ok(respuesta);
    }


    @GetMapping("/choferes/disponibles")
    public ResponseEntity<UsuariosResponseDTO> obtenerChoferesDisponibles() {

        List<Usuario> usuarios = usuarioRepository
                .findByRol_NombreAndDisponible("CHOFER", true);

        List<UsuarioChoferDTO> data = usuarios.stream().map(u -> {
            String nombre = safeDecrypt(u.getNombre());
            String apellido = safeDecrypt(u.getApellido());
            String correo = safeDecrypt(u.getCorreo());
            String celular = safeDecrypt(u.getCelular());

            return UsuarioChoferDTO.builder()
                    .id(u.getUsuario_id())
                    .nombre((nombre != null ? nombre : "") + " " + (apellido != null ? apellido : ""))
                    .email(correo)
                    .telefono(celular)
                    .tipo("chofer")
                    .disponible(Boolean.TRUE.equals(u.getDisponible()))
                    .vehiculo_asignado((VehiculoAsignado) null)
                    .build();
        }).toList();

        UsuariosResponseDTO respuesta = UsuariosResponseDTO.builder()
                .success(true)
                .data(data)
                .build();

        return ResponseEntity.ok(respuesta);
    }
}
