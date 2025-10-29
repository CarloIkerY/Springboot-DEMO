package com.example.demo.config;

import com.example.demo.model.Rol;
import com.example.demo.model.Usuario;
import com.example.demo.repo.RolRepository;
import com.example.demo.repo.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    @PostConstruct
    public void init() {
        if (rolRepository.count() == 0) {
            Rol admin = Rol.builder().nombre("ADMIN").build();
            Rol mecanico = Rol.builder().nombre("MECANICO").build();

            rolRepository.save(admin);
            rolRepository.save(mecanico);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            usuarioRepository.save(Usuario.builder()
                    .nombre(AESUtil.encrypt("Admin"))
                    .apellido(AESUtil.encrypt("Principal"))
                    .correo(AESUtil.encrypt("admin@admin.com"))
                    .contrasena(encoder.encode("admin123")) // Luego usar BCrypt
                    .rol(admin)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nombre(AESUtil.encrypt("Carlos"))
                    .apellido(AESUtil.encrypt("Mecánico"))
                    .correo(AESUtil.encrypt("mecanico@demo.com"))
                    .contrasena(encoder.encode("mecanico123"))
                    .rol(mecanico)
                    .build());
        }
    }
}
