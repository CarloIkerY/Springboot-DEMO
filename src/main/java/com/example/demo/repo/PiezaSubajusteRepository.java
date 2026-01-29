package com.example.demo.repo;

import com.example.demo.model.Pieza_subajuste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PiezaSubajusteRepository extends JpaRepository<Pieza_subajuste, Integer> {
}
