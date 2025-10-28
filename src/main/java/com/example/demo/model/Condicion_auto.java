package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToOne
    @JoinColumn(name = "auto_id", nullable = false)
    private Auto auto;

    @Column(nullable = false)
    private LocalDateTime fecha_origen;

    @Column(nullable = false, length = 200)
    private String descripcion;

    @Column(nullable = false, length = 200)
    private String observaciones;

    @Column(nullable = false)
    private Boolean faros_luces;

    @Column(nullable = false)
    private Boolean cuartos_luces;

    @Column(nullable = false)
    private Boolean espejo_lateral_izq;

    @Column(nullable = false)
    private Boolean espejo_lateral_der;

    @Column(nullable = false)
    private Boolean emblemas;

    @Column(nullable = false)
    private Boolean tapones_ruedas;

    @Column(nullable = false)
    private Boolean molduras_completas;

    @Column(nullable = false)
    private Boolean calaveras;

    @Column(nullable = false)
    private Boolean tapon_gasolina;

    @Column(nullable = false)
    private Boolean carroceria_sin_golpes;

    @Column(nullable = false)
    private Boolean bocinas_claxon;

    @Column(nullable = false)
    private Boolean tarjeta_circulacion;

    @Column(nullable = false)
    private Boolean comprobante_verificacion;

    @Column(nullable = false)
    private Boolean seguro_vigente;

    @Column(nullable = false)
    private Boolean gato;

    @Column(nullable = false)
    private Boolean maneral_gato;

    @Column(nullable = false)
    private Boolean llave_cruz;

    @Column(nullable = false)
    private Boolean estuche_herramientas;

    @Column(nullable = false)
    private Boolean triangulo_seguridad;

    @Column(nullable = false)
    private Boolean llantas_refaccion;

    @Column(nullable = false)
    private Boolean seguros_birlos;

    @Column(nullable = false)
    private Boolean instrumentos_tablero;

    @Column(nullable = false)
    private Boolean testigos_advertencia;

    @Column(nullable = false)
    private Boolean mecanismos_limpia_parabrisas;

    @Column(nullable = false)
    private Boolean plumas_limpia_parabrisas;

    @Column(nullable = false)
    private Boolean tipo_radio;

    @Column(nullable = false)
    private Boolean botones_radio;

    @Column(nullable = false)
    private Boolean encendedor;

    @Column(nullable = false)
    private Boolean espejo_retrovisor;

    @Column(nullable = false)
    private Boolean ceniceros;

    @Column(nullable = false)
    private Boolean cinturones;

    @Column(nullable = false, length = 4)
    private Integer numero_tapetes;

    @Column(nullable = false)
    private Boolean seguros_puertas;

    @Column(nullable = false)
    private Boolean manijas_interiores;

    @Column(nullable = false)
    private Boolean pomo_palanca;

    @Column(nullable = false)
    private Boolean elevadores_cristales;

    @Column(nullable = false)
    private Boolean aire_acondicionado;

    @Column(nullable = false)
    private Boolean tapon_aceite;

    @Column(nullable = false)
    private Boolean tapon_radiador;

    @Column(nullable = false)
    private Boolean tapos_liquido_frenos;

    @Column(nullable = false)
    private Boolean tapon_direccion_hidraulica;

    @Column(nullable = false)
    private Boolean tapon_deposito_limpia_parabrisas;

    @Column(nullable = false)
    private Boolean varillas_aceite;

    @Column(nullable = false)
    private Boolean filtro_aire;

    @Column(nullable = false)
    private Boolean bateria;

    @Column(nullable = false, length = 200)
    private String otros;
}
