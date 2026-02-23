package com.example.demo.repo;

import com.example.demo.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActividadRespository extends JpaRepository<Actividad, Long> {
    @Query("SELECT a FROM Actividad a WHERE a.subajuste.subajuste_id = :id")
    List<Actividad> findAllBySubajusteId(@Param("id") Long id);
}
