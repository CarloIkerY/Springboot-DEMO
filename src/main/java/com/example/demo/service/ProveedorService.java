package com.example.demo.service;

import com.example.demo.dto.ProveedorDTO;
import com.example.demo.model.Proveedor;
import com.example.demo.repo.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public List<Proveedor> listarProveedores() {
        return proveedorRepository.findAll();
    }

    public Object ejecutarAccion(ProveedorDTO dto) {

        if (dto.getAccion() == null || dto.getAccion().trim().isEmpty()) {
            throw new RuntimeException("La acción es obligatoria (AGREGAR o ELIMINAR).");
        }

        String accion = dto.getAccion().trim().toUpperCase();

        switch (accion) {

            case "AGREGAR" -> {

                if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
                    throw new RuntimeException("El nombre del proveedor es obligatorio.");
                }


                String telefono = (dto.getTelefono() == null || dto.getTelefono().trim().isEmpty())
                        ? "N/A"
                        : dto.getTelefono().trim();

                String correo = (dto.getCorreo() == null || dto.getCorreo().trim().isEmpty())
                        ? "N/A"
                        : dto.getCorreo().trim();

                Proveedor nuevo = Proveedor.builder()
                        .nombre(dto.getNombre().trim())
                        .telefono(telefono)
                        .correo(correo)
                        .build();

                return proveedorRepository.save(nuevo);
            }

            case "ELIMINAR" -> {

                if (dto.getProveedor_id() == null) {
                    throw new RuntimeException("El proveedor_id es obligatorio para eliminar.");
                }

                Proveedor proveedor = proveedorRepository.findById(dto.getProveedor_id())
                        .orElseThrow(() -> new RuntimeException("Proveedor no encontrado."));

                proveedorRepository.delete(proveedor);

                return dto.getProveedor_id();
            }

            default -> throw new RuntimeException("Acción inválida. Usa AGREGAR o ELIMINAR.");
        }
    }
}
