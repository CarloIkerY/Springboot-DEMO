package com.example.demo.repo;

import com.example.demo.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findByNombreAndDependenciaAndTelefonoOficina(
            String nombre,
            String dependencia,
            String telefonoOficina
    );
}
