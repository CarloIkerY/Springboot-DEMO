package com.example.demo.controller;

import com.example.demo.dto.UsuarioChoferDTO;
import com.example.demo.dto.UsuariosResponseDTO;
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

    // GET /usuarios/choferes/disponibles
    @GetMapping("/choferes/disponibles")
    public ResponseEntity<UsuariosResponseDTO> obtenerChoferesDisponibles() {

        // Busca todos los usuarios con rol CHOFER y disponibles = true
        List<Usuario> usuarios = usuarioRepository
                .findByRol_NombreAndDisponible("CHOFER", true);

        // Mapea Usuario -> UsuarioChoferDTO
        List<UsuarioChoferDTO> data = usuarios.stream().map(u ->
                UsuarioChoferDTO.builder()
                        .id(u.getUsuario_id())
                        .nombre(
                                (u.getNombre() != null ? u.getNombre() : "") + " " +
                                        (u.getApellido() != null ? u.getApellido() : "")
                        )
                        .email(u.getCorreo())
                        .telefono(u.getCelular())
                        .tipo(u.getRol() != null ? u.getRol().getNombre().toLowerCase() : null)
                        .disponible(Boolean.TRUE.equals(u.getDisponible()))
                        .vehiculo_asignado(null) // por ahora sin vehículo asignado directo
                        .build()
        ).toList();

        // Arma response envolviendo la lista
        UsuariosResponseDTO respuesta = UsuariosResponseDTO.builder()
                .success(true)
                .data(data)
                .build();

        return ResponseEntity.ok(respuesta);
    }
}
