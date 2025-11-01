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
        // String nombreEncriptado = AESUtil.encrypt(dto.getNombre());
        // String celularEncriptado = AESUtil.encrypt(dto.getCelular());
        // String direccionEncriptado = AESUtil.encrypt(dto.getDireccion());

        Cliente cliente = Cliente.builder()
                .nombre(dto.getNombre())
                .celular(dto.getCelular())
                .direccion(dto.getDireccion())
                .build();

        cliente = clienteRepository.save(cliente);

        return toDTO(cliente);
    }

    public ClienteConAutoDTO createClienteConAuto(ClienteConAutoDTO dto) {
        // Buscar si ya existe el cliente
        Cliente cliente = null;

        // Revisar si existe el cliente con alguno de los autos
        // String nombreEncriptado = AESUtil.encrypt(dto.getNombre());
        // String celularEncriptado = AESUtil.encrypt(dto.getCelular());
        // String placaEncriptada = AESUtil.encrypt(autoDTO.getPlaca())
        Optional<Cliente> clienteExistenteOpt = clienteRepository.findByNombreAndCelular(
                dto.getNombre(),
                dto.getCelular()
        );

        if (clienteExistenteOpt.isPresent()) {
            cliente = clienteExistenteOpt.get();
        }

        if (cliente != null) {
            for (AutoDTO autoDTO : dto.getAutos()) {
                boolean yaRegistrado = cliente.getAutos().stream()
                        .anyMatch(a -> a.getPlaca().equals(autoDTO.getPlaca()));

                if (!yaRegistrado) {
                    Auto nuevoAuto = Auto.builder()
                            .marca(autoDTO.getMarca())
                            .modelo(autoDTO.getModelo())
                            .anio(autoDTO.getAnio())
                            .placa(autoDTO.getPlaca())
                            .color(autoDTO.getColor())
                            .cliente(cliente)
                            .build();

                    cliente.getAutos().add(nuevoAuto);
                }
            }
        } else {
            // Cliente nuevo → crear con todos sus autos
            cliente = Cliente.builder()
                    .nombre(dto.getNombre())
                    .celular(dto.getCelular())
                    .direccion(dto.getDireccion())
                    .build();

            List<Auto> autos = new ArrayList<>();
            for (AutoDTO autoDTO : dto.getAutos()) {
                Auto auto = Auto.builder()
                        .marca(autoDTO.getMarca())
                        .modelo(autoDTO.getModelo())
                        .anio(autoDTO.getAnio())
                        .placa(autoDTO.getPlaca())
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
                        //.placa(AESUtil.decrypt(auto.getPlaca()))
                        .placa(auto.getPlaca())
                        .color(auto.getColor())
                        .build()
        ).toList();

        // String nombreDesencriptado = AESUtil.decrypt(cliente.getNombre());
        // String celularDesencriptado = AESUtil.decrypt(cliente.getCelular());
        // String direccionDesencriptado = AESUtil.decrypt(cliente.getDireccion());

        return ClienteConAutoDTO.builder()
                .nombre(cliente.getNombre())
                .celular(cliente.getCelular())
                .direccion(cliente.getDireccion())
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
        // String placaEncrypt = AESUtil.encrypt(placa);

        List<Cliente> clientes = clienteRepository.findByPlacaAuto(placa);

        return clientes.stream()
                .map(this::toClienteConAutosRespuestaDTO)
                .collect(Collectors.toList());
    }

    public List<ClienteConAutoDTO> findClientesByModeloAuto(String modelo) {
        List<Cliente> clientes = clienteRepository.findByModeloAuto(modelo);

        return clientes.stream()
                .map(this::toClienteConAutosRespuestaDTO)
                .collect(Collectors.toList());
    }

    public List<ClienteConAutoDTO> findClientesByNombre(String nombre) {
        // String nombreDesencriptado = AESUtil.decrypt(nombre);

        List<Cliente> clientes = clienteRepository.findByNombre(nombre);

        return clientes.stream()
                .map(this::toClienteConAutosRespuestaDTO)
                .collect(Collectors.toList());
    }

    public List<ClienteConAutoDTO> findClientesByCelular(String celular) {
        // String celularDesencriptado = AESUtil.decrypt(celular);

        List<Cliente> clientes = clienteRepository.findByCelular(celular);

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