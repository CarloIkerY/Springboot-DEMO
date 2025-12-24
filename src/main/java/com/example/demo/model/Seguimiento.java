package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Seguimiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @OneToMany(mappedBy = "ajusteAuto_id", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Ajuste_auto> ajusteAutos = new ArrayList<>();
}
