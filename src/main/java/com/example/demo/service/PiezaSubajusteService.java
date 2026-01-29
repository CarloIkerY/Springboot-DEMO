package com.example.demo.service;

import com.example.demo.dto.AgregarPiezasSubajusteBatchRequest;
import com.example.demo.dto.PiezaSubajusteResponseDTO;
import com.example.demo.dto.response.PiezaSubajusteBatchResponseDTO;
import com.example.demo.mappers.PiezaSubajusteMapper;
import com.example.demo.model.*;
import com.example.demo.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PiezaSubajusteService {

    private final OrdenRepository ordenRepository;
    private final SubajusteRepository subajusteRepository;
    private final PiezaRepository piezaRepository;
    private final ProveedorRepository proveedorRepository;
    private final PiezaSubajusteRepository piezaSubajusteRepository;

    private final PiezaSubajusteMapper piezaSubajusteMapper;

    @Transactional
    public PiezaSubajusteBatchResponseDTO agregarBatch(AgregarPiezasSubajusteBatchRequest req) {

        Orden orden = ordenRepository.findByIdWithAllRelations(req.getOrdenId())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada: " + req.getOrdenId()));

        Subajuste subajuste = subajusteRepository.findById(req.getSubajusteId())
                .orElseThrow(() -> new RuntimeException("Subajuste no encontrado: " + req.getSubajusteId()));

        // Validación subajuste pertenezca a la orden
        boolean pertenece = orden.getSeguimiento().getAjusteAutos().stream()
                .flatMap(a -> a.getSubajustes().stream())
                .anyMatch(s -> s.getSubajuste_id().equals(subajuste.getSubajuste_id()));
        if (!pertenece) {
            throw new RuntimeException("El subajuste " + subajuste.getSubajuste_id() + " no pertenece a la orden " + orden.getOrden_id());
        }

        List<PiezaSubajusteResponseDTO> out = new ArrayList<>();

        for (var item : req.getItems()) {
            Proveedor proveedor = proveedorRepository.findById(item.getProveedorId())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado: " + item.getProveedorId()));

            Pieza pieza = piezaRepository.findById(item.getPiezaId())
                    .orElseThrow(() -> new RuntimeException("Pieza no encontrada: " + item.getPiezaId()));

            Pieza_subajuste entity = Pieza_subajuste.builder()
                    .subajuste(subajuste)
                    .proveedor(proveedor)
                    .pieza(pieza)
                    .cantidad(item.getCantidad())
                    .fecha_solicitud(new Date())
                    .costo_unitario_compra(item.getCostoUnitario())
                    .subtotal(item.getCantidad() * item.getCostoUnitario())
                    .build();

            entity = piezaSubajusteRepository.save(entity);

            PiezaSubajusteResponseDTO dto = piezaSubajusteMapper.toDto(entity);

            dto.setOrdenId(req.getOrdenId());

            out.add(dto);
        }

        return PiezaSubajusteBatchResponseDTO.builder()
                .ordenId(req.getOrdenId())
                .subajusteId(req.getSubajusteId())
                .piezas(out)
                .build();

    }
}
