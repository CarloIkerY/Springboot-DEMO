package com.example.demo.repo;

import com.example.demo.model.Condicion_auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CondicionAutoRepository extends JpaRepository<Condicion_auto, Long> {

    @Query("SELECT c FROM Condicion_auto c WHERE c.auto.auto_id = :autoId AND c.estado_actual = true")
    Optional<Condicion_auto> findActiveByAutoId(@Param("autoId") Long autoId);
}
