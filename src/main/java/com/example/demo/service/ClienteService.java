package com.example.demo.service;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.model.Cliente;
import com.example.demo.repo.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteDTO createUser(ClienteDTO dto){
        Cliente cliente = Cliente.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();

        cliente = clienteRepository.save(cliente);

        return toDTO(cliente);
    }

    public List<ClienteDTO> getAllUsers(){
        return clienteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ClienteDTO toDTO(Cliente cliente) {
        return ClienteDTO.builder()
                .name(cliente.getName())
                .email(cliente.getEmail())
                .build();
    }
}