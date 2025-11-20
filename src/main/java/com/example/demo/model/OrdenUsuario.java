package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "orden_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordenusuario_id;

    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    @JsonBackReference(value = "orden-asignaciones")
    private Orden orden;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties({"ordenUsuarios"})
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDate fecha_asignacion;
}
