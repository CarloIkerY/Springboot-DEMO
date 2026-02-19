package com.example.demo.repo;

import com.example.demo.model.OrdenPieza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface OrdenPiezaRepository extends JpaRepository<OrdenPieza, Long> {

    // BUSCAR pieza por orden + pieza
    @Query("""
        SELECT op
        FROM OrdenPieza op
        WHERE op.orden.orden_id = :ordenId
          AND op.pieza.pieza_id = :piezaId
    """)
    Optional<OrdenPieza> findByOrdenIdAndPiezaId(@Param("ordenId") Long ordenId,
                                                 @Param("piezaId") Long piezaId);


    // ELIMINAR pieza por orden + pieza

    @Modifying
    @Transactional
    @Query("""
        DELETE FROM OrdenPieza op
        WHERE op.orden.orden_id = :ordenId
          AND op.pieza.pieza_id = :piezaId
    """)
    int deleteByOrdenIdAndPiezaId(@Param("ordenId") Long ordenId,
                                  @Param("piezaId") Long piezaId);
}
