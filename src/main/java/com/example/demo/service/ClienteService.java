package com.example.demo.service;
import com.example.demo.dto.*;

import com.example.demo.model.Auto;
import com.example.demo.model.Cliente;
import com.example.demo.model.Empresa;
import com.example.demo.model.Empresa_cliente;
import com.example.demo.repo.ClienteRepository;
import com.example.demo.repo.EmpresaClienteRepository;
import com.example.demo.repo.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmpresaClienteRepository empresaClienteRepository;
    private final EmpresaRepository empresaRepository;

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

        Empresa_cliente empresaCliente = null;

        // EMPRESA / EMPRESA_CLIENTE
        if (dto.getEmpresa() != null && dto.getEmpresa().getEmpresa() != null) {

            EmpresaClienteDTO ecDTO = dto.getEmpresa();
            EmpresaDTO eDTO = ecDTO.getEmpresa();

            String nombreEmpresa = eDTO.getNombre();
            String dependencia = ecDTO.getDependencia();
            String telefono = ecDTO.getTelefonoOficina();

            boolean tieneEmpresa =
                    nombreEmpresa != null && !nombreEmpresa.isBlank()
                            && dependencia != null && !dependencia.isBlank()
                            && telefono != null && !telefono.isBlank();

            if (tieneEmpresa) {

                // EMPRESA
                Empresa empresa = empresaRepository
                        .findByNombre(nombreEmpresa)
                        .orElseGet(() ->
                                empresaRepository.save(
                                        Empresa.builder()
                                                .nombre(nombreEmpresa)
                                                .build()
                                )
                        );

                // EMPRESA_CLIENTE
                empresaCliente = empresaClienteRepository
                        .findByEmpresaAndDependenciaAndTelefonoOficina(
                                empresa, dependencia, telefono
                        )
                        .orElseGet(() ->
                                empresaClienteRepository.save(
                                        Empresa_cliente.builder()
                                                .empresa(empresa)
                                                .dependencia(dependencia)
                                                .telefonoOficina(telefono)
                                                .build()
                                )
                        );
            }
        }

        // CLIENTE
        Cliente cliente = Cliente.builder()
                .nombre(dto.getNombre())
                .celular(dto.getCelular())
                .direccion(dto.getDireccion())
                .empresa_cliente(empresaCliente)
                .autos(new ArrayList<>())
                .build();

        // AUTOS
        if (dto.getAutos() != null) {
            for (AutoDTO a : dto.getAutos()) {
                cliente.getAutos().add(
                        Auto.builder()
                                .marca(a.getMarca())
                                .modelo(a.getModelo())
                                .anio(a.getAnio())
                                .placa(a.getPlaca())
                                .color(a.getColor())
                                .numero_serie(a.getNumero_serie())
                                .cliente(cliente)
                                .build()
                );
            }
        }

        // RESPUESTA
        return toClienteConAutosRespuestaDTO(clienteRepository.save(cliente));
    }

    private ClienteConAutoDTO toClienteConAutosRespuestaDTO(Cliente cliente) {

        List<AutoDTO> autosDTO = cliente.getAutos().stream()
                .map(auto -> AutoDTO.builder()
                        .auto_id(auto.getAuto_id())
                        .marca(auto.getMarca())
                        .modelo(auto.getModelo())
                        .anio(auto.getAnio())
                        .placa(auto.getPlaca())
                        .color(auto.getColor())
                        .numero_serie(auto.getNumero_serie())
                        .build()
                )
                .toList();

        ClienteConAutoDTO.ClienteConAutoDTOBuilder builder =
                ClienteConAutoDTO.builder()
                        .cliente_id(cliente.getCliente_id())
                        .nombre(cliente.getNombre())
                        .telefono(cliente.getTelefono())
                        .celular(cliente.getCelular())
                        .correo(cliente.getCorreo())
                        .direccion(cliente.getDireccion())
                        .autos(autosDTO);

        if (cliente.getEmpresa_cliente() != null) {
            Empresa_cliente ec = cliente.getEmpresa_cliente();
            Empresa e = ec.getEmpresa();

            builder.empresa(
                    EmpresaClienteDTO.builder()
                            .empresaCliente_id(ec.getEmpresaCliente_id())
                            .empresa(
                                    EmpresaDTO.builder()
                                            .empresa_id(e.getEmpresa_id())
                                            .nombre(e.getNombre())
                                            .build()
                            )
                            .dependencia(ec.getDependencia())
                            .telefonoOficina(ec.getTelefonoOficina())
                            .build()
            );
        }

        return builder.build();
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

    public List<ClienteConAutoDTO> findClientesByColorAuto(String color) {
        List<Cliente> clientes = clienteRepository.findByColorAuto(color);

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

    public List<ClienteConAutoDTO> findClientesByDireccion(String direccion) {
        // String celularDesencriptado = AESUtil.decrypt(celular);

        List<Cliente> clientes = clienteRepository.findByDireccion(direccion);

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

    private ClienteConAutoDTO convertirClienteConAutosDTO(Cliente cliente) {

        List<AutoDTO> autosDTO = cliente.getAutos().stream()
                .map(auto -> AutoDTO.builder()
                        .auto_id(auto.getAuto_id())
                        .marca(auto.getMarca())
                        .modelo(auto.getModelo())
                        .anio(auto.getAnio())
                        .placa(auto.getPlaca())
                        .color(auto.getColor())
                        .numero_serie(auto.getNumero_serie())
                        .build()
                )
                .toList();

        ClienteConAutoDTO.ClienteConAutoDTOBuilder builder =
                ClienteConAutoDTO.builder()
                        .cliente_id(cliente.getCliente_id())
                        .nombre(cliente.getNombre())
                        .celular(cliente.getCelular())
                        .direccion(cliente.getDireccion())
                        .autos(autosDTO);

        if (cliente.getEmpresa_cliente() != null) {
            Empresa_cliente ec = cliente.getEmpresa_cliente();
            Empresa e = ec.getEmpresa();

            builder.empresa(
                    EmpresaClienteDTO.builder()
                            .empresaCliente_id(ec.getEmpresaCliente_id())
                            .empresa(
                                    EmpresaDTO.builder()
                                            .empresa_id(e.getEmpresa_id())
                                            .nombre(e.getNombre())
                                            .build()
                            )
                            .dependencia(ec.getDependencia())
                            .telefonoOficina(ec.getTelefonoOficina())
                            .build()
            );
        }

        return builder.build();
    }

    public ClienteConAutoDTO agregarAutoCliente(ClienteConAutoDTO dto) {

        if (dto.getCliente_id() == null) {
            throw new RuntimeException("El ID del cliente es obligatorio");
        }

        if (dto.getAutos() == null || dto.getAutos().isEmpty()) {
            throw new RuntimeException("Los datos del auto son obligatorios");
        }

        Cliente cliente = clienteRepository.findById(dto.getCliente_id())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        for (AutoDTO a : dto.getAutos()) {
            Auto nuevoAuto = new Auto();
            nuevoAuto.setMarca(a.getMarca());
            nuevoAuto.setModelo(a.getModelo());
            nuevoAuto.setAnio(a.getAnio());
            nuevoAuto.setPlaca(a.getPlaca());
            nuevoAuto.setColor(a.getColor());
            nuevoAuto.setNumero_serie(a.getNumero_serie());
            nuevoAuto.setCliente(cliente);

            cliente.getAutos().add(nuevoAuto);
        }

        Cliente clienteGuardado = clienteRepository.save(cliente);

        return convertirClienteConAutosDTO(clienteGuardado);
    }

    public ClienteConAutoDTO actualizarCliente(Long id, ModificarClienteDTO dto) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (dto.getNombre() != null) {
            cliente.setNombre(dto.getNombre());
        }

        if (dto.getCelular() != null) {
            cliente.setCelular(dto.getCelular());
        }

        if (dto.getDireccion() != null) {
            cliente.setDireccion(dto.getDireccion());
        }



        Cliente clienteGuardado = clienteRepository.save(cliente);


        return convertirClienteConAutosDTO(clienteGuardado);
    }
}

