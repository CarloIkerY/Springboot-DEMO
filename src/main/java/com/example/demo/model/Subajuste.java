package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "subajuste", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Actividad> actividades =  new HashSet<>();

    @OneToMany(mappedBy = "subajuste", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pieza_subajuste> piezaSubajustes;

    @Column(nullable = false)
    private Boolean terminado = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subajuste)) return false;
        Subajuste that = (Subajuste) o;
        return subajuste_id != null && subajuste_id.equals(that.subajuste_id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}