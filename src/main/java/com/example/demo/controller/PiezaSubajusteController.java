package com.example.demo.controller;

import com.example.demo.dto.AgregarPiezasSubajusteBatchRequest;
import com.example.demo.service.PiezaSubajusteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pieza-subajustes")
@RequiredArgsConstructor
public class PiezaSubajusteController {

    private final PiezaSubajusteService service;

    @PostMapping("/batch")
    public ResponseEntity<Map<String, Object>> agregarBatch(@RequestBody AgregarPiezasSubajusteBatchRequest req) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", service.agregarBatch(req)); // ✅ aquí va service
        response.put("message", "Piezas agregadas correctamente al subajuste.");
        return ResponseEntity.ok(response);
    }
}
