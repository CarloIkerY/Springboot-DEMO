package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "Condicion_auto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Condicion_auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long condicion_auto_id;

    @ManyToOne
    @JoinColumn(name = "auto_id", nullable = false)
    private Auto auto;

    @Column(nullable = false)
    private LocalDateTime fecha_origen;

    @Column(nullable = false, length = 200)
    private String descripcion;

    @Column(nullable = false, length = 200)
    private String observaciones;

    @Column(nullable = false, length = 20)
    private Integer kilometraje;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private Map<String, Object> detalles;

    @Column(nullable = false)
    private Boolean estado_actual;
}
