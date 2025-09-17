package com.example.demo.security;

import com.example.demo.model.Usuario;
import com.example.demo.repo.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Verifica si un usuario tiene un rol permitido
     * @param usuarioId ID del usuario a verificar
     * @param allowedRoleIds IDs de los roles permitidos (ej. 1 = ADMIN, 2 = AGENTE)
     * @return true si tiene acceso, false si no
     */
    public boolean tieneAcceso(Long usuarioId, Long... allowedRoleIds) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) return false;

        Long userRoleId = usuarioOpt.get().getRol().getId();
        for (Long allowedId : allowedRoleIds) {
            if (userRoleId.equals(allowedId)) {
                return true;
            }
        }
        return false;
    }
}
