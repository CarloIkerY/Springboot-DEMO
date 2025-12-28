package com.example.demo.repo;

import com.example.demo.model.Ajuste_auto;
import com.example.demo.model.Subajuste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubajusteRepository extends JpaRepository<Subajuste, Long> {
    Optional<Subajuste> findByAjusteAutoAndDescripcionIgnoreCase(Ajuste_auto ajusteAuto, String descripcion);
}