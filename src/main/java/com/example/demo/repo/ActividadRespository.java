package com.example.demo.repo;

import com.example.demo.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActividadRespository extends JpaRepository<Actividad, Long> {
}
