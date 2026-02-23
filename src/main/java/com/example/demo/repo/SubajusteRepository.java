package com.example.demo.repo;

import com.example.demo.model.Actividad;
import com.example.demo.model.Ajuste_auto;
import com.example.demo.model.Subajuste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubajusteRepository extends JpaRepository<Subajuste, Long> {
    Optional<Subajuste> findByAjusteAutoAndDescripcionIgnoreCase(Ajuste_auto ajusteAuto, String descripcion);

    @Query("""
        SELECT s
        FROM Subajuste s
        WHERE s.ajusteAuto.ajusteAuto_id = :id
    """)
    List<Subajuste> findAllByAjusteAutoId(@Param("id") Long id);
}