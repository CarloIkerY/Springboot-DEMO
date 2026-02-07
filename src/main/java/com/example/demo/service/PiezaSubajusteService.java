package com.example.demo.service;

import com.example.demo.dto.request.AgregarPiezaSubajusteRequestDTO;
import com.example.demo.model.*;
import com.example.demo.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PiezaSubajusteService {

    private final PiezaSubajusteRepository piezaSubajusteRepository;
    private final PiezaProveedorPrecioRepository piezaProveedorPrecioRepository;

    private final PiezaRepository piezaRepository;
    private final ProveedorRepository proveedorRepository;
    private final SubajusteRepository subajusteRepository;
    private final OrdenRepository ordenRepository;

    @Transactional
    public Pieza_subajuste agregarPiezaProveedorAOrden(AgregarPiezaSubajusteRequestDTO req) {

        Proveedor proveedor = proveedorRepository.findById(req.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado: " + req.getProveedorId()));

        Pieza pieza = piezaRepository.findById(req.getPiezaId())
                .orElseThrow(() -> new RuntimeException("Pieza no encontrada: " + req.getPiezaId()));

        Subajuste subajuste = subajusteRepository.findById(req.getSubajusteId())
                .orElseThrow(() -> new RuntimeException("Subajuste no encontrado: " + req.getSubajusteId()));

        Orden orden = ordenRepository.findById(req.getOrdenId())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada: " + req.getOrdenId()));

        // ✅ Validación real: subajuste -> ajusteAuto -> seguimiento -> orden
        if (subajuste.getAjusteAuto() == null ||
                subajuste.getAjusteAuto().getSeguimiento() == null ||
                subajuste.getAjusteAuto().getSeguimiento().getOrden() == null ||
                !subajuste.getAjusteAuto().getSeguimiento().getOrden().getOrden_id().equals(orden.getOrden_id())
        ) {
            throw new RuntimeException("El subajuste " + req.getSubajusteId()
                    + " NO pertenece a la orden " + req.getOrdenId());
        }

        Pieza_subajuste ps = Pieza_subajuste.builder()
                .subajuste(subajuste)
                .pieza(pieza)
                .proveedor(proveedor)
                .cantidad(req.getCantidad())
                .fecha_recibido(null)
                .costo_unitario_compra(req.getCostoUnitario())
                // subtotal y fecha_solicitud se calculan en @PrePersist
                .build();

        ps = piezaSubajusteRepository.save(ps);

        PiezaProveedorPrecio hist = PiezaProveedorPrecio.builder()
                .pieza(pieza)
                .proveedor(proveedor)
                .cantidad(req.getCantidad())
                .costoUnitario(req.getCostoUnitario())
                .piezaSubajusteId(ps.getPiezaSubajuste_id()) // ✅ Long -> Long
                .build();

        piezaProveedorPrecioRepository.save(hist);

        return ps;
    }
}
