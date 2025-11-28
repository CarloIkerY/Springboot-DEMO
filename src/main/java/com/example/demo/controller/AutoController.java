package com.example.demo.controller;

import com.example.demo.dto.CondicionAutoDTO;
import com.example.demo.model.Condicion_auto;
import com.example.demo.service.AutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/autos")
@RequiredArgsConstructor
public class AutoController {

    @Autowired
    private AutoService autoService;

    @PostMapping("/condicion/registrar")
    public ResponseEntity<?> registrarCondicion(@RequestBody CondicionAutoDTO dto) {
        Condicion_auto guardado = autoService.registrarCondicion(dto);

        return ResponseEntity.ok(Map.of(
                "data", guardado,
                "message", "Condición registrada correctamente"
        ));
    }
}
