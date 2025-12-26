package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Ajuste")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ajuste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ajuste_id;

    @Column(nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "ajuste", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Ajuste_auto> ajusteAutos;
}
