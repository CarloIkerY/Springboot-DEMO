package com.example.demo.repo;

import com.example.demo.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByNombreAndCelular(String nombre, String celular);

    @Query("SELECT c FROM Cliente c JOIN c.autos a WHERE a.placa = :placa")
    List<Cliente> findByPlacaAuto(String placa);
    @Query("SELECT c FROM Cliente c JOIN c.autos a WHERE a.modelo = :modelo")
    List<Cliente> findByModeloAuto(String modelo);
    @Query("SELECT c FROM Cliente c JOIN c.autos a WHERE a.color = :color")
    List<Cliente> findByColorAuto(String color);

    List<Cliente> findByNombre(String nombre);
    List<Cliente> findByCelular(String celular);
    List<Cliente> findByDireccion(String direccion);
}