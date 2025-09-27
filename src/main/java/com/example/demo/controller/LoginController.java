package com.example.demo.controller;

import com.example.demo.config.AESUtil;
import com.example.demo.config.AESUtil;
import com.example.demo.dto.LoginResponse;
import com.example.demo.model.Usuario;
import com.example.demo.repo.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.beans.Encoder;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginRequest) {
        String encryptedEmail = AESUtil.encrypt(loginRequest.getEmail());
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(
                encryptedEmail
        );

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if(passwordEncoder.matches(loginRequest.getContrasena(),usuario.getContrasena())) {
                LoginResponse response = new LoginResponse(
                        AESUtil.decrypt(usuario.getNombre()),
                        usuario.getId() // aquí enviamos usuarioId en lugar de rol
                );
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            }
        } else {
            return ResponseEntity.status(404).body("Credenciales inválidas");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }

}
