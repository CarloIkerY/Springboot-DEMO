package com.example.demo.controller;

import com.example.demo.dto.UsuarioChoferDTO;
import com.example.demo.dto.UsuariosResponseDTO;
import com.example.demo.model.Usuario;
import com.example.demo.repo.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    @GetMapping("/choferes/disponibles")
    public ResponseEntity<UsuariosResponseDTO> obtenerChoferesDisponibles() {
        return ResponseEntity.ok(obtenerDisponiblesPorRol("CHOFER"));
    }

    @GetMapping("/mecanicos/disponibles")
    public ResponseEntity<UsuariosResponseDTO> obtenerMecanicosDisponibles() {
        return ResponseEntity.ok(obtenerDisponiblesPorRol("MECANICO"));
    }


    private UsuariosResponseDTO obtenerDisponiblesPorRol(String rol) {


        List<Usuario> usuarios = usuarioRepository.findByRol_NombreAndDisponible(rol, true);
        if (usuarios == null) usuarios = Collections.emptyList();

        List<UsuarioChoferDTO> data = usuarios.stream()
                .map(this::toUsuarioChoferDTO)
                .toList();

        return UsuariosResponseDTO.builder()
                .success(true)
                .data(data)
                .build();
    }

    private UsuarioChoferDTO toUsuarioChoferDTO(Usuario u) {

        String nombre = ((u.getNombre() != null ? u.getNombre() : "") + " " +
                (u.getApellido() != null ? u.getApellido() : "")).trim();

        String tipo = (u.getRol() != null && u.getRol().getNombre() != null)
                ? u.getRol().getNombre().toLowerCase()
                : null;

        return UsuarioChoferDTO.builder()
                .id(u.getUsuario_id())
                .nombre(nombre)
                .email(u.getCorreo())
                .telefono(u.getCelular())
                .tipo(tipo)
                .disponible(Boolean.TRUE.equals(u.getDisponible()))
                .vehiculo_asignado(null)
                .build();
    }
}


