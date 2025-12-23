package com.example.demo.repo;

import com.example.demo.model.Empresa;
import com.example.demo.model.Empresa_cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaClienteRepository extends JpaRepository<Empresa_cliente, Long> {
    Optional<Empresa_cliente> findByEmpresaAndDependenciaAndTelefonoOficina(
            Empresa empresa,
            String dependencia,
            String telefonoOficina
    );
}
