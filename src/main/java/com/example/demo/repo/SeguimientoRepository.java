package com.example.demo.repo;

import com.example.demo.model.Seguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {
    @Query("SELECT s FROM Seguimiento s WHERE s.orden.orden_id = :ordenId")
    Optional<Seguimiento> findByOrdenId(@Param("ordenId") Long ordenId);
}
