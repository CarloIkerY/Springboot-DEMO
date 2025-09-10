package com.example.demo.config;

import com.example.demo.model.Rol;
import com.example.demo.model.Usuario;
import com.example.demo.repo.RolRepository;
import com.example.demo.repo.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
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
            Rol agente = Rol.builder().nombre("AGENTE").build();
            Rol mecanico = Rol.builder().nombre("MECANICO").build();

            rolRepository.save(admin);
            rolRepository.save(agente);
            rolRepository.save(mecanico);

            usuarioRepository.save(Usuario.builder()
                    .nombre("Admin")
                    .apellido("Principal")
                    .email("admin@admin.com")
                    .contrasena("admin123") // Luego usar BCrypt
                    .rol(admin)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nombre("Agente")
                    .apellido("Ventas")
                    .email("agente@demo.com")
                    .contrasena("agente123")
                    .rol(agente)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nombre("Carlos")
                    .apellido("Mecánico")
                    .email("mecanico@demo.com")
                    .contrasena("mecanico123")
                    .rol(mecanico)
                    .build());
        }
    }
}
