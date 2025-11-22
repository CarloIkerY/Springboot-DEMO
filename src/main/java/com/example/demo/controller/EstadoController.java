package com.example.demo.controller;

import com.example.demo.model.Estado;
import com.example.demo.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/estados")
@RequiredArgsConstructor
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @GetMapping("/listado")
    public ResponseEntity<?> obtenerEstados() {
        List<Estado> estados = estadoService.obtenerListado();

        return ResponseEntity.ok(Map.of(
                "message", "Lista de estados obtenida correctamente",
                "data", estados
        ));
    }
}
