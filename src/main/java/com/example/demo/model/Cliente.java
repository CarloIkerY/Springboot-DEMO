package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cliente_id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = true, length = 50)
    private String telefono;

    @Column(nullable = false, length = 50)
    private String celular;

    @Column(nullable = true, length = 100)
    private String correo;

    @Column(nullable = false, length = 100)
    private String direccion;

    @Column(nullable = false)
    private Boolean clienteUNAM;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Auto> autos = new ArrayList<>();
}