package com.example.demo.service;

import com.example.demo.model.Pieza;
import com.example.demo.repo.PiezaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PiezaService {
    private final PiezaRepository piezaRepository;

    public List<Pieza> listarPiezas() {
        return  piezaRepository.findAll();
    }
}
