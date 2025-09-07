package com.example.demo.repo;

import com.example.demo.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByNombreAndEmailAndTelefono(String nombre, String email, String telefono);

    @Query("SELECT c FROM Cliente c JOIN c.autos a WHERE a.placa = :placa")
    List<Cliente> findByPlacaAuto(String placa);

    List<Cliente> findByNombre(String nombre);
    List<Cliente> findByEmail(String email);
    List<Cliente> findByTelefono(String telefono);
}