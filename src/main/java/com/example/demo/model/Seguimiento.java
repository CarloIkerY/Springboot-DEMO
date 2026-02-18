package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Seguimiento")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Seguimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seguimiento_id;

    @OneToOne
    @JoinColumn(name = "orden_id", nullable = false)
    @JsonBackReference
    private Orden orden;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = true)
    @JsonManagedReference(value="estado-seguimientos")
    private Estado estado;

    @Column(nullable = false)
    private LocalDateTime fecha_actualizacion;

    @Column(nullable = true)
    private Boolean ajustes_aceptados;

    @OneToMany(mappedBy = "seguimiento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Ajuste_auto> ajusteAutos = new HashSet<>();
}
