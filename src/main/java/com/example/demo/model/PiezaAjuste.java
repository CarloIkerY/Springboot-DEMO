package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PiezaAjuste")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PiezaAjuste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pieza_ajuste_id;

    @ManyToOne
    @JoinColumn(name = "ajuste_id", nullable = false)
    private Ajuste ajuste;

    @ManyToOne
    @JoinColumn(name = "pieza_id", nullable = false)
    private Pieza pieza;

    private Integer cantidad;
}