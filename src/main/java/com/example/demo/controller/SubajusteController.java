package com.example.demo.controller;

import com.example.demo.dto.request.SubajusteRequestDTO;
import com.example.demo.dto.request.SubajusteTerminadoRequestDTO;
import com.example.demo.dto.response.OrdenResponseDTO;
import com.example.demo.service.SubajusteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/subajustes")
@RequiredArgsConstructor
public class SubajusteController {

    private final SubajusteService subajusteService;

    @PostMapping("/crearSubajustes")
    public ResponseEntity<?> crearSubajustes(@RequestBody SubajusteRequestDTO dto) {
        Map<String, Object> response = new HashMap<>();

        if(dto.getOrden_id() == null){
            response.put("message", "La orden es obligatoria.");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }

        if(dto.getAjusteAuto_id() == null){
            response.put("message", "El ajuste es obligatorio.");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }

        if(dto.getSubajustes() == null || dto.getSubajustes().isEmpty()){
            response.put("message", "La lista de subajustes no puede estar vacía.");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }

        OrdenResponseDTO result = subajusteService.crearSubajustes(dto.getOrden_id(), dto.getAjusteAuto_id(), dto.getSubajustes());

        response.put("message", "Subajustes creados correctamente.");
        response.put("data", result);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/terminarSubajustes")
    public ResponseEntity<?> terminarSubajustes(@RequestBody SubajusteTerminadoRequestDTO dto) {
        Map<String, Object> response = new HashMap<>();

        if(dto.getOrden_id() == null) {
            response.put("message","La orden_id es obligatoria.");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }

        if(dto.getAjusteAuto_id() == null) {
            response.put("message", "El ajusteAuto es obligatorio.");
            response.put("data",null);
            return ResponseEntity.badRequest().body(response);
        }

        if(dto.getSubajustes() == null || dto.getSubajustes().isEmpty()){
            response.put("message", "El/Los subajuste(s) es obligatorio.");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }

        OrdenResponseDTO result = subajusteService.finalizarSubajustes(
                dto.getOrden_id(),
                dto.getAjusteAuto_id(),
                dto.getSubajustes()
        );

        response.put("message", "Subajustes creados correctamente.");
        response.put("data", result);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
