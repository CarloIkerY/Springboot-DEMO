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

import java.util.ArrayList;
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
        Cliente cliente = null;

        // Revisar si existe el cliente con alguno de los autos
        Optional<Cliente> clienteExistenteOpt = clienteRepository.findByNombreAndCelular(
                AESUtil.encrypt(dto.getNombre()),
                AESUtil.encrypt(dto.getCelular())
        );

        if (clienteExistenteOpt.isPresent()) {
            cliente = clienteExistenteOpt.get();
        }

        if (cliente != null) {
            for (AutoDTO autoDTO : dto.getAutos()) {
                boolean yaRegistrado = cliente.getAutos().stream()
                        .anyMatch(a -> AESUtil.decrypt(a.getPlaca()).equals(autoDTO.getPlaca()));

                if (!yaRegistrado) {
                    Auto nuevoAuto = Auto.builder()
                            .marca(autoDTO.getMarca())
                            .modelo(autoDTO.getModelo())
                            .anio(autoDTO.getAnio())
                            .placa(AESUtil.encrypt(autoDTO.getPlaca()))
                            .color(autoDTO.getColor())
                            .cliente(cliente)
                            .build();

                    cliente.getAutos().add(nuevoAuto);
                }
            }
        } else {
            // Cliente nuevo → crear con todos sus autos
            cliente = Cliente.builder()
                    .nombre(AESUtil.encrypt(dto.getNombre()))
                    .celular(AESUtil.encrypt(dto.getCelular()))
                    .direccion(AESUtil.encrypt(dto.getDireccion()))
                    .build();

            List<Auto> autos = new ArrayList<>();
            for (AutoDTO autoDTO : dto.getAutos()) {
                Auto auto = Auto.builder()
                        .marca(autoDTO.getMarca())
                        .modelo(autoDTO.getModelo())
                        .anio(autoDTO.getAnio())
                        .placa(AESUtil.encrypt(autoDTO.getPlaca()))
                        .color(autoDTO.getColor())
                        .cliente(cliente)
                        .build();

                autos.add(auto);
            }

            cliente.setAutos(autos);
        }

        // Guardar cliente
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
        List<Cliente> clientes = clienteRepository.findByPlacaAuto(AESUtil.encrypt(placa));

        return clientes.stream()
                .map(this::toClienteConAutosRespuestaDTO)
                .collect(Collectors.toList());
    }

    public List<ClienteConAutoDTO> findClientesByNombre(String nombre) {
        List<Cliente> clientes = clienteRepository.findByNombre(AESUtil.encrypt(nombre));

        return clientes.stream()
                .map(this::toClienteConAutosRespuestaDTO)
                .collect(Collectors.toList());
    }

    public List<ClienteConAutoDTO> findClientesByCelular(String celular) {
        List<Cliente> clientes = clienteRepository.findByCelular(AESUtil.encrypt(celular));

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