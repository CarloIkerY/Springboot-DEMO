package com.example.demo.repo;

import com.example.demo.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
    @Query(
        """
            SELECT o FROM Orden o
            JOIN o.seguimientos s
            WHERE s.seguimiento_id = (
                SELECT MAX(s2.seguimiento_id)
                FROM Seguimiento s2
                WHERE s2.orden = o
            )
            AND s.estado.estado_id IN :ids
        """)
    List<Orden> findOrdenesPorEstadoActual(@Param("ids") List<Long> ids);
}
