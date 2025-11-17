package com.example.demo.controller;

import com.example.demo.config.AESUtil;
import com.example.demo.dto.UsuarioChoferDTO;
import com.example.demo.dto.UsuariosResponseDTO;
import com.example.demo.model.Auto;
import com.example.demo.model.Usuario;
import com.example.demo.dto.VehiculoAsignado;
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

    @GetMapping
    public ResponseEntity<UsuariosResponseDTO> obtenerUsuarios(
            @RequestParam String tipo,
            @RequestParam Boolean disponible
    ) {

        //Busca usuarios por ROL y disponible (ej. tipo=chofer → ROL=CHOFER)
        List<Usuario> usuarios = usuarioRepository.findByRol_NombreAndDisponible(
                tipo.toUpperCase(), disponible
        );

        // Mapear lista de Usuario   -  lista de UsuarioChoferDTO
        List<UsuarioChoferDTO> data = usuarios.stream().map(u -> {

            // tomar el primer auto asignado (si tiene)
            Auto auto = u.getAutos() != null && !u.getAutos().isEmpty()
                    ? u.getAutos().get(0)
                    : null;

            VehiculoAsignado vehiculoAsignado = null;

            if (auto != null) {
                vehiculoAsignado = VehiculoAsignado.builder()
                        .id(auto.getAuto_id())
                        .marca(auto.getMarca())
                        .modelo(auto.getModelo())
                        .placas(auto.getPlaca())
                        .capacidad("N/A")
                        .build();
            }

            //  cifrados  AES
            String nombre = AESUtil.decrypt(u.getNombre());
            String apellido = AESUtil.decrypt(u.getApellido());
            String correo = AESUtil.decrypt(u.getCorreo());

            return UsuarioChoferDTO.builder()
                    .id(u.getUsuario_id())
                    .nombre(nombre + " " + apellido)
                    .email(correo)
                    .telefono("N/A")
                    .tipo(u.getRol() != null ? u.getRol().getNombre().toLowerCase() : null)
                    .disponible(Boolean.TRUE.equals(u.getDisponible()))
                    .vehiculo_asignado(vehiculoAsignado)
                    .build();
        }).toList();

        // wrapper de respuesta
        UsuariosResponseDTO respuesta = UsuariosResponseDTO.builder()
                .success(true)
                .data(data)
                .build();

        return ResponseEntity.ok(respuesta);
    }
}
