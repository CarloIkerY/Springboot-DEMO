package com.example.demo.controller;

import com.example.demo.dto.request.AgregarPiezaSubajusteRequestDTO;
import com.example.demo.dto.response.PiezaSubajusteResponseDTO;
import com.example.demo.mappers.PiezaSubajusteMapper;
import com.example.demo.model.Pieza_subajuste;
import com.example.demo.service.PiezaSubajusteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pieza-subajuste")
@RequiredArgsConstructor
public class PiezaSubajusteController {

    private final PiezaSubajusteService piezaSubajusteService;
    private final PiezaSubajusteMapper piezaSubajusteMapper;

    @PostMapping("/agregar")
    public ResponseEntity<PiezaSubajusteResponseDTO> agregar(@Valid @RequestBody AgregarPiezaSubajusteRequestDTO req) {
        Pieza_subajuste entity = piezaSubajusteService.agregarPiezaProveedorAOrden(req);
        return ResponseEntity.ok(piezaSubajusteMapper.toDto(entity));
    }
}
