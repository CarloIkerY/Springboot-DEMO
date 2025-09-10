package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Mecanico")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mecanico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mecanico_id;

    private String nombre;
    private String especialidad;

    @OneToMany(mappedBy = "mecanico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seguimiento> seguimiento;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
