package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Empresa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empresa_id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Empresa_cliente> empresaClientes = new ArrayList<>();
}
