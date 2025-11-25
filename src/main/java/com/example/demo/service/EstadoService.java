package com.example.demo.service;

import com.example.demo.model.Estado;
import com.example.demo.repo.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> obtenerListado() {
        return estadoRepository.findAll();
    }
}

