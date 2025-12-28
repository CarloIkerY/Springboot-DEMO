package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Subajuste")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subajuste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subajuste_id;

    @ManyToOne
    @JoinColumn(name = "ajusteAuto_id", nullable = false)
    private Ajuste_auto ajusteAuto;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fecha_registro;

    @OneToMany(mappedBy = "subajuste", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Actividad> actividades;

    @OneToMany(mappedBy = "subajuste", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pieza_subajuste> piezaSubajustes;
}