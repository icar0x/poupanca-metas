package com.icaro.poupanca_metas.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico_juros")
@Getter
@Setter
@NoArgsConstructor
public class HistoricoJuros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "meta_id", nullable = false)
    private Meta meta;

    @Column(name = "juros_calculados", nullable = false)
    private BigDecimal jurosCalculados;

    @Column(name = "data_calculo", nullable = false)
    private LocalDateTime dataCalculo = LocalDateTime.now();

    @Column(name = "saldo_apos_juros", nullable = false)
    private BigDecimal saldoAposJuros;
}