package com.example.demo.service;
import com.example.demo.dto.ModificarClienteDTO;

import com.example.demo.dto.AutoDTO;
import com.example.demo.dto.ClienteConAutoDTO;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.dto.EmpresaDTO;
import com.example.demo.dto.ModificarClienteDTO;
import com.example.demo.model.Auto;
import com.example.demo.model.Cliente;
import com.example.demo.model.Empresa;
import com.example.demo.repo.ClienteRepository;
import com.example.demo.repo.EmpresaRepository;
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

        // 1. Determinar si aplica empresa
        Empresa empresa = null;

        if (dto.getEmpresa() != null) {

            boolean tieneEmpresa =
                    dto.getEmpresa().getNombreEmpresa() != null && !dto.getEmpresa().getNombreEmpresa().trim().isEmpty()
                            && dto.getEmpresa().getDependenciaEmpresa() != null && !dto.getEmpresa().getDependenciaEmpresa().trim().isEmpty()
                            && dto.getEmpresa().getTelefonoOficinaEmpresa() != null && !dto.getEmpresa().getTelefonoOficinaEmpresa().trim().isEmpty();

            if (tieneEmpresa) {
                empresa = empresaRepository
                        .findByNombreAndDependenciaAndTelefonoOficina(
                                dto.getEmpresa().getNombreEmpresa(),
                                dto.getEmpresa().getDependenciaEmpresa(),
                                dto.getEmpresa().getTelefonoOficinaEmpresa()
                        )
                        .orElseGet(() -> {
                            Empresa nuevaEmpresa = Empresa.builder()
                                    .nombre(dto.getEmpresa().getNombreEmpresa())
                                    .dependencia(dto.getEmpresa().getDependenciaEmpresa())
                                    .telefonoOficina(dto.getEmpresa().getTelefonoOficinaEmpresa())
                                    .build();
                            return empresaRepository.save(nuevaEmpresa);
                        });
            }
        }

        // 2. Buscar o crear cliente
        Cliente cliente = clienteRepository
                .findByNombreAndCelular(dto.getNombre(), dto.getCelular())
                .orElseGet(() -> Cliente.builder()
                        .nombre(dto.getNombre())
                        .celular(dto.getCelular())
                        .direccion(dto.getDireccion())
                        .autos(new ArrayList<>())
                        .build()
                );

        // 3. Asignar empresa (puede quedar null y es válido)
        cliente.setEmpresa(empresa);

        // 4. Agregar autos si no existen
        for (AutoDTO autoDTO : dto.getAutos()) {

            boolean yaExiste = cliente.getAutos().stream()
                    .anyMatch(a -> a.getPlaca().equals(autoDTO.getPlaca()));

            if (!yaExiste) {
                Auto auto = Auto.builder()
                        .marca(autoDTO.getMarca())
                        .modelo(autoDTO.getModelo())
                        .anio(autoDTO.getAnio())
                        .placa(autoDTO.getPlaca())
                        .color(autoDTO.getColor())
                        .numero_serie(autoDTO.getNumero_serie())
                        .cliente(cliente)
                        .build();

                cliente.getAutos().add(auto);
            }
        }

        // 5. Guardar cliente (cascade guarda autos)
        cliente = clienteRepository.save(cliente);

        return toClienteConAutosRespuestaDTO(cliente);
    }


    private ClienteConAutoDTO toClienteConAutosRespuestaDTO(Cliente cliente) {

        // 1. Mapear autos
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

        // 2. Construir DTO base del cliente
        ClienteConAutoDTO.ClienteConAutoDTOBuilder builder =
                ClienteConAutoDTO.builder()
                        .cliente_id(cliente.getCliente_id())
                        .nombre(cliente.getNombre())
                        .telefono(cliente.getTelefono())
                        .celular(cliente.getCelular())
                        .correo(cliente.getCorreo())
                        .direccion(cliente.getDireccion())
                        .autos(autosDTO);

        // 3. Mapear empresa SOLO si existe relación
        if (cliente.getEmpresa() != null) {
            Empresa empresa = cliente.getEmpresa();

            EmpresaDTO empresaDTO = EmpresaDTO.builder()
                    .empresa_id(empresa.getEmpresa_id())
                    .nombreEmpresa(empresa.getNombre())
                    .dependenciaEmpresa(empresa.getDependencia())
                    .telefonoOficinaEmpresa(empresa.getTelefonoOficina())
                    .build();

            builder.empresa(empresaDTO);
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

        // ✅ MAPEO CORRECTO DE EMPRESA
        if (cliente.getEmpresa() != null) {
            builder.empresa(
                    EmpresaDTO.builder()
                            .empresa_id(cliente.getEmpresa().getEmpresa_id())
                            .nombreEmpresa(cliente.getEmpresa().getNombre())
                            .dependenciaEmpresa(cliente.getEmpresa().getDependencia())
                            .telefonoOficinaEmpresa(cliente.getEmpresa().getTelefonoOficina())
                            .build()
            );
        }

        return builder.build();
    }

    public ClienteConAutoDTO agregarAutoCliente(ClienteConAutoDTO dto) {

        if (dto.getCliente_id() == null) {
            throw new RuntimeException("El ID del cliente es obligatorio");
        }

        if (dto.getAuto() == null) {
            throw new RuntimeException("Los datos del auto son obligatorios");
        }

        Cliente cliente = clienteRepository.findById(dto.getCliente_id())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Auto nuevoAuto = new Auto();
        nuevoAuto.setMarca(dto.getAuto().getMarca());
        nuevoAuto.setModelo(dto.getAuto().getModelo());
        nuevoAuto.setAnio(dto.getAuto().getAnio());
        nuevoAuto.setPlaca(dto.getAuto().getPlaca());
        nuevoAuto.setColor(dto.getAuto().getColor());
        nuevoAuto.setNumero_serie(dto.getAuto().getNumero_serie());


        nuevoAuto.setCliente(cliente);
        cliente.getAutos().add(nuevoAuto);

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

