package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Ajuste_auto")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Ajuste_auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ajusteAuto_id;

    @ManyToOne
    @JoinColumn(name = "ajuste_id", nullable = false)
    @JsonBackReference
    private Ajuste ajuste;

    @ManyToOne
    @JoinColumn(name = "seguimiento_id", nullable = false)
    private Seguimiento seguimiento;

    @Column(nullable = true)
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fecha_creacion;

    @Column(nullable = true)
    private LocalDateTime fecha_finalizado;

    @OneToMany(mappedBy = "ajusteAuto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Subajuste> subajustes = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ajuste_auto)) return false;
        Ajuste_auto that = (Ajuste_auto) o;
        return ajusteAuto_id != null && ajusteAuto_id.equals(that.ajusteAuto_id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
