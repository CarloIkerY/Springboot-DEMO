package com.example.demo.repo;

import com.example.demo.model.Seguimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {
}
