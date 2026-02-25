package com.example.demo.service;

import com.example.demo.dto.OrdenDTO;
import com.example.demo.model.*;
import com.example.demo.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenService {

    private final AutoRepository autoRepository;
    private final OrdenRepository ordenRepository;
    private final SeguimientoRepository seguimientoRepository;
    private final EstadoRepository estadoRepository;
    private final UsuarioRepository usuarioRepository;

//ESTADO FINALIZA ORDEN SOLO GERENTE
    private static final Long ESTADO_ENTREGADO_ID = 15L;

    public Orden crearOrden(OrdenDTO dto) {

        Auto auto = autoRepository.findById(dto.getAuto_id())
                .orElseThrow(() -> new RuntimeException("Auto no encontrado"));

        Estado estado = estadoRepository.findById(dto.getEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        // 1️⃣ Crear la orden
        Orden orden = Orden.builder()
                .auto(auto)
                .numero_orden(generarNumeroOrden())
                .fecha_creacion(LocalDate.now())
                .ordenUsuarios(new ArrayList<>())
                .build();

        // 2️⃣ Crear seguimiento inicial
        Seguimiento seguimiento = Seguimiento.builder()
                .orden(orden) // lado dueño
                .estado(estado)
                .fecha_actualizacion(LocalDateTime.now())
                .build();

        // 3️⃣ Relación bidireccional (1–1)
        orden.setSeguimiento(seguimiento);

        // 4️⃣ Guardar
        return ordenRepository.save(orden);
    }

    private String generarNumeroOrden() {
        return "ORD-" + System.currentTimeMillis();
    }

    public List<Orden> getOrdenesPorEstados(List<Long> ids) {
        return ordenRepository.findOrdenesPorEstadoActual(ids);
    }

    public Orden asignarOrden(OrdenDTO dto) {

        // 1️⃣ Obtener orden
        Orden orden = ordenRepository.findById(dto.getOrden_id())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        // 2️⃣ Obtener seguimiento (1–1)
        Seguimiento seguimiento = orden.getSeguimiento();
        if (seguimiento == null) {
            throw new RuntimeException("La orden no tiene seguimiento asignado");
        }

        Estado estadoActual = seguimiento.getEstado();
        if (estadoActual == null || estadoActual.getEstado_id() == null) {
            throw new RuntimeException("El seguimiento no tiene un estado asignado");
        }

        // 3️⃣ Obtener usuario
        Usuario usuario = usuarioRepository.findById(dto.getUsuario_id())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 4️⃣ Determinar nuevo estado
        Estado estadoAsignado;

        if (usuario.getRol().getRol_id() == 2) {
            estadoAsignado = estadoRepository.findById(2L)
                    .orElseThrow(() -> new RuntimeException("Estado 2 no encontrado"));

        } else if (usuario.getRol().getRol_id() == 3 && estadoActual.getEstado_id() < 5) {
            estadoAsignado = estadoRepository.findById(5L)
                    .orElseThrow(() -> new RuntimeException("Estado 5 no encontrado"));

        } else if (usuario.getRol().getRol_id() == 3 && estadoActual.getEstado_id() >= 5) {
            estadoAsignado = estadoActual;

        } else {
            throw new RuntimeException("El rol del usuario no es válido para asignación.");
        }

        // 5️⃣ Actualizar seguimiento (estado actual)
        seguimiento.setEstado(estadoAsignado);
        seguimiento.setFecha_actualizacion(LocalDateTime.now());

        // 6️⃣ Registrar asignación (historial)
        OrdenUsuario asignacion = new OrdenUsuario();
        asignacion.setOrden(orden);
        asignacion.setUsuario(usuario);
        asignacion.setFecha_asignacion(LocalDate.now());

        orden.getOrdenUsuarios().add(asignacion);

        // 7️⃣ Guardar
        return ordenRepository.save(orden);
    }

    public Orden agendarFechaRecoleccion(OrdenDTO dto) {

        Orden orden = ordenRepository.findById(dto.getOrden_id())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Orden no encontrada"
                ));

        Seguimiento seguimiento = orden.getSeguimiento();
        if (seguimiento == null) {
            throw new RuntimeException("La orden no tiene seguimiento asignado");
        }

        Estado estadoAsignado = estadoRepository.findById(3L)
                .orElseThrow(() -> new RuntimeException("Estado 3 no encontrado"));

        // Actualizar estado y fecha
        seguimiento.setEstado(estadoAsignado);
        seguimiento.setFecha_actualizacion(LocalDateTime.now());

        orden.setFecha_recoleccion(dto.getFecha_recoleccion());

        return ordenRepository.save(orden);
    }

    public Orden cambiarEstado(OrdenDTO dto) {

        // 1️⃣ Obtener la orden
        Orden orden = ordenRepository.findById(dto.getOrden_id())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        // 2️⃣ Obtener el usuario
        Usuario usuario = usuarioRepository.findById(dto.getUsuario_id())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 3️⃣ Validar rol GERENTE (rol_id = 4)
        if (usuario.getRol().getRol_id() != 4) {
            throw new RuntimeException(
                    "Solo un usuario GERENTE puede cambiar el estado manualmente."
            );
        }

        // 4️⃣ Obtener seguimiento (1–1)
        Seguimiento seguimiento = orden.getSeguimiento();
        if (seguimiento == null) {
            throw new RuntimeException("La orden no tiene seguimiento asignado.");
        }

        // 5️⃣ Obtener nuevo estado
        Estado nuevoEstado = estadoRepository.findById(dto.getEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        // 6️⃣ Actualizar seguimiento
        seguimiento.setEstado(nuevoEstado);
        seguimiento.setFecha_actualizacion(LocalDateTime.now());

        // 7️⃣ Registrar historial de asignación (opcional)
        OrdenUsuario asignacion = new OrdenUsuario();
        asignacion.setOrden(orden);
        asignacion.setUsuario(usuario);
        asignacion.setFecha_asignacion(LocalDate.now());
        orden.getOrdenUsuarios().add(asignacion);

        // 8️⃣ Guardar
        return ordenRepository.save(orden);
    }

    public List<Orden> obtenerOrdenesAsignadasAChofer(Long choferId) {
        return ordenRepository.findOrdenesAsignadasAChofer(choferId);
    }

    public List<Orden> obtenerOrdenesAsignadasAMecanico(Long mecanicoId) {
        return ordenRepository.findOrdenesAsignadasAMecanico(mecanicoId);
    }

    // ✅ NUEVO: Finalizar orden (entrega al cliente) - SOLO GERENTE
    @Transactional
    public Orden finalizarOrden(Long ordenId, Long usuarioId, LocalDateTime fechaEntrega) {

        Orden orden = ordenRepository.findById(ordenId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Orden no encontrada: " + ordenId
                ));

        // idempotente: si ya está finalizada, regresa igual
        if (orden.getFecha_entrega() != null) {
            return orden;
        }

        if (usuarioId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El usuarioId es obligatorio para finalizar la orden"
            );
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado: " + usuarioId
                ));

        // SOLO GERENTE (rol_id = 4)
        if (usuario.getRol().getRol_id() != 4) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Solo un usuario GERENTE puede finalizar la orden"
            );
        }

        Seguimiento seguimiento = orden.getSeguimiento();
        if (seguimiento == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La orden no tiene seguimiento asignado"
            );
        }

        // set fecha entrega (si no mandas, usa now)
        orden.setFecha_entrega(fechaEntrega != null ? fechaEntrega : LocalDateTime.now());

        // Cambiar estado a ENTREGADO/FINALIZADA
        Estado estadoFinal = estadoRepository.findById(ESTADO_ENTREGADO_ID)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Estado final (ENTREGADO) no encontrado: " + ESTADO_ENTREGADO_ID
                ));

        seguimiento.setEstado(estadoFinal);
        seguimiento.setFecha_actualizacion(LocalDateTime.now());

        // registrar historial de acción del gerente
        OrdenUsuario asignacion = new OrdenUsuario();
        asignacion.setOrden(orden);
        asignacion.setUsuario(usuario);
        asignacion.setFecha_asignacion(LocalDate.now());
        orden.getOrdenUsuarios().add(asignacion);

        return ordenRepository.save(orden);
    }
}