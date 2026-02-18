package com.example.demo.controller;

import com.example.demo.dto.request.ActividadRequestDTO;
import com.example.demo.dto.request.ActividadTerminadaRequestDTO;
import com.example.demo.dto.response.OrdenResponseDTO;
import com.example.demo.service.ActividadService;
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
@RequestMapping("/actividades")
@RequiredArgsConstructor
public class ActividadController {

    private final ActividadService actividadService;

    @PostMapping("/crearActividades")
    public ResponseEntity<?> crearActividades(@RequestBody ActividadRequestDTO dto) {
        Map<String, Object> response = new HashMap<>();

        // Validaciones básicas
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

        if(dto.getSubajuste_id() == null){
            response.put("message", "El subajuste es obligatorio.");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }

        if(dto.getActividades() == null || dto.getActividades().isEmpty()){
            response.put("message", "La lista de actividades no puede estar vacía.");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }

        // Llamada al servicio
        OrdenResponseDTO result = actividadService.agregarActividades(
                dto.getOrden_id(),
                dto.getAjusteAuto_id(),
                dto.getSubajuste_id(),
                dto.getActividades()
        );

        response.put("message", "Actividades agregadas correctamente.");
        response.put("data", result);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/terminarActividades")
    public ResponseEntity<?> terminarActividades(@RequestBody ActividadTerminadaRequestDTO dto) {
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
        if(dto.getSubajuste_id() == null){
            response.put("message", "El subajuste es obligatorio.");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }
        if(dto.getActividadesId() == null || dto.getActividadesId().isEmpty()){
            response.put("message", "La lista de actividades no puede estar vacía.");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }

        // Llamada al servicio
        OrdenResponseDTO result = actividadService.finalizarActividades(
                dto.getOrden_id(),
                dto.getAjusteAuto_id(),
                dto.getSubajuste_id(),
                dto.getActividadesId()
        );
        response.put("message", "Actividades agregadas correctamente.");
        response.put("data", result);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
