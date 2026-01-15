package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Actividad")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actividad_id;

    @ManyToOne
    @JoinColumn(name = "subajuste_id", nullable = false)
    private Subajuste subajuste;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fecha_registro;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actividad)) return false;
        Actividad that = (Actividad) o;
        return actividad_id != null && actividad_id.equals(that.actividad_id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
