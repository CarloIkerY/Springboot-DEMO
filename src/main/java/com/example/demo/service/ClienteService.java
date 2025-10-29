package com.example.demo.service;

import com.example.demo.config.AESUtil;
import com.example.demo.dto.AutoDTO;
import com.example.demo.dto.ClienteConAutoDTO;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.model.Auto;
import com.example.demo.model.Cliente;
import com.example.demo.repo.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public ClienteDTO createCliente(ClienteDTO dto){
        Cliente cliente = Cliente.builder()
                .nombre(AESUtil.encrypt(dto.getNombre()))
                .celular(AESUtil.encrypt(dto.getCelular()))
                .direccion(AESUtil.encrypt(dto.getDireccion()))
                .build();

        cliente = clienteRepository.save(cliente);

        return toDTO(cliente);
    }

    public ClienteConAutoDTO createClienteConAuto(ClienteConAutoDTO dto) {
        // Buscar si ya existe el cliente
        Optional<Cliente> clienteExistenteOpt = clienteRepository.findByNombreAndCorreoAndTelefono(
                dto.getNombre(), dto.getCorreo(), dto.getTelefono());

        Cliente cliente;
        boolean clienteExistente;

        // Tomar el primer auto (como ya haces)
        AutoDTO autoDTO = dto.getAutos().get(0);

        if (clienteExistenteOpt.isPresent()) {
            // ✅ Cliente ya existe → agregar nuevo auto
            cliente = clienteExistenteOpt.get();

            Auto nuevoAuto = Auto.builder()
                    .marca(autoDTO.getMarca())
                    .modelo(autoDTO.getModelo())
                    .anio(autoDTO.getAnio())
                    .placa(AESUtil.encrypt(autoDTO.getPlaca()))
                    .color(autoDTO.getColor())
                    .cliente(cliente)
                    .build();

            cliente.getAutos().add(nuevoAuto);
        } else {
            // Cliente no existe → crear cliente con auto
            cliente = Cliente.builder()
                    .nombre(AESUtil.encrypt(dto.getNombre()))
                    .celular(AESUtil.encrypt(dto.getCelular()))
                    .direccion(AESUtil.encrypt(dto.getDireccion()))
                    .build();

            Auto auto = Auto.builder()
                    .marca(autoDTO.getMarca())
                    .modelo(autoDTO.getModelo())
                    .anio(autoDTO.getAnio())
                    .placa(AESUtil.encrypt(autoDTO.getPlaca()))
                    .color(autoDTO.getColor())
                    .cliente(cliente)
                    .build();

            cliente.setAutos(List.of(auto));
        }

        // Guardar (actualiza o crea)
        cliente = clienteRepository.save(cliente);

        return toClienteConAutosRespuestaDTO(cliente);
    }

    private ClienteConAutoDTO toClienteConAutosRespuestaDTO(Cliente cliente) {
        List<AutoDTO> autosDTO = cliente.getAutos().stream().map(auto ->
                AutoDTO.builder()
                        .marca(auto.getMarca())
                        .modelo(auto.getModelo())
                        .anio(auto.getAnio())
                        .placa(AESUtil.decrypt(auto.getPlaca()))
                        .color(auto.getColor())
                        .build()
        ).toList();

        return ClienteConAutoDTO.builder()
                .nombre(AESUtil.decrypt(cliente.getNombre()))
                .celular(AESUtil.decrypt(cliente.getCelular()))
                .direccion(AESUtil.decrypt(cliente.getDireccion()))
                .autos(autosDTO)
                .build();
    }


    public List<ClienteDTO> getAllUsers(){
        return clienteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ClienteConAutoDTO> findClientesByPlaca(String placa) {
        List<Cliente> clientes = clienteRepository.findByPlacaAuto(placa);

        return clientes.stream()
                .map(this::toClienteConAutosRespuestaDTO)
                .collect(Collectors.toList());
    }

    public List<ClienteConAutoDTO> findClientesByNombre(String nombre) {
        List<Cliente> clientes = clienteRepository.findByNombre(nombre);

        return clientes.stream()
                .map(this::toClienteConAutosRespuestaDTO)
                .collect(Collectors.toList());
    }

    public List<ClienteConAutoDTO> findClientesByCorreo(String correo) {
        List<Cliente> clientes = clienteRepository.findByCorreo(correo);

        return clientes.stream()
                .map(this::toClienteConAutosRespuestaDTO)
                .collect(Collectors.toList());
    }

    public List<ClienteConAutoDTO> findClientesByTelefono(String telefono) {
        List<Cliente> clientes = clienteRepository.findByTelefono(telefono);

        return clientes.stream()
                .map(this::toClienteConAutosRespuestaDTO)
                .collect(Collectors.toList());
    }

    private ClienteDTO toDTO(Cliente cliente) {
        return ClienteDTO.builder()
                .nombre(cliente.getNombre())
                .celular(cliente.getCelular())
                .direccion(cliente.getDireccion())
                .build();
    }
}