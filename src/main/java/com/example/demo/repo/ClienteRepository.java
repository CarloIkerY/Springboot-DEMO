package com.example.demo.repo;

import com.example.demo.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByNombreAndCorreoAndTelefono(String nombre, String correo, String telefono);

    @Query("SELECT c FROM Cliente c JOIN c.autos a WHERE a.placa = :placa")
    List<Cliente> findByPlacaAuto(String placa);

    List<Cliente> findByNombre(String nombre);
    List<Cliente> findByCorreo(String correo);
    List<Cliente> findByTelefono(String telefono);
}