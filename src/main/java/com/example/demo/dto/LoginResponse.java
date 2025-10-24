package com.example.demo.dto;

public class LoginResponse {
    private String nombre;
    private Long usuarioId;

    public LoginResponse(String nombre, Long usuarioId) {
        this.nombre = nombre;
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }
}
