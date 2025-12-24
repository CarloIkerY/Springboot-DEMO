package com.example.demo.repo;

import com.example.demo.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Long> {

    // 🔹 ORDENES POR ESTADO ACTUAL (Seguimiento 1–1)
    @Query("""
        SELECT o
        FROM Orden o
        JOIN o.seguimiento s
        WHERE s.estado.estado_id IN :ids
    """)
    List<Orden> findOrdenesPorEstadoActual(@Param("ids") List<Long> ids);


    // 🔹 ORDENES ASIGNADAS A CHOFER (última asignación)
    @Query("""
        SELECT o
        FROM Orden o
        JOIN o.ordenUsuarios ou
        WHERE ou.usuario.usuario_id = :choferId
          AND ou.fecha_asignacion = (
              SELECT MAX(ou2.fecha_asignacion)
              FROM OrdenUsuario ou2
              WHERE ou2.orden = o
                AND ou2.usuario.usuario_id = :choferId
          )
        ORDER BY ou.fecha_asignacion DESC
    """)
    List<Orden> findOrdenesAsignadasAChofer(@Param("choferId") Long choferId);


    // 🔹 ORDENES ASIGNADAS A MECÁNICO (última asignación)
    @Query("""
        SELECT o
        FROM Orden o
        JOIN o.ordenUsuarios ou
        WHERE ou.usuario.usuario_id = :mecanicoId
          AND ou.fecha_asignacion = (
              SELECT MAX(ou2.fecha_asignacion)
              FROM OrdenUsuario ou2
              WHERE ou2.orden = o
                AND ou2.usuario.usuario_id = :mecanicoId
          )
        ORDER BY ou.fecha_asignacion DESC
    """)
    List<Orden> findOrdenesAsignadasAMecanico(@Param("mecanicoId") Long mecanicoId);

}

