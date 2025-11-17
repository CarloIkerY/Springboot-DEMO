package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    @JsonBackReference
    private Orden orden;

    @ManyToOne
    @JoinColumn(name = "ajuste_id", nullable = true)
    private Ajuste ajuste;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = true)
    @JsonManagedReference(value="estado-seguimientos")
    private Estado estado;

    @Column(nullable = false)
    private LocalDate fecha_actualizacion;
}
