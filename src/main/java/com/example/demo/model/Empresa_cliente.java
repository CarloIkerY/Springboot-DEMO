package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Empresa_cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Empresa_cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empresaCliente_id;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

    @Column(nullable = false, length = 50)
    private String dependencia;

    @Column(nullable = false, length = 50)
    private String telefonoOficina;

    @OneToMany(mappedBy = "empresa_cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Cliente> clientes = new ArrayList<>();
}