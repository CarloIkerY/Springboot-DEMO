package com.example.demo.controller;

import com.example.demo.model.Pieza;
import com.example.demo.service.PiezaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pieza")
@RequiredArgsConstructor
public class PiezaController {
    @Autowired
    private PiezaService piezaService;

    @GetMapping("/listado")
    public ResponseEntity<?> obtenerPiezas() {
        List<Pieza> piezas = piezaService.listarPiezas();

        return ResponseEntity.ok(Map.of(
                "data", piezas,
                "message", "Lista de piezas obtenida correctamente"
        ));
    }
}
