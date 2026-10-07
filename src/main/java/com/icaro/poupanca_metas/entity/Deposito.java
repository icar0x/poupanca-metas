package com.icaro.poupanca_metas.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "deposito")
@Getter
@Setter
@NoArgsConstructor
public class Deposito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "meta_id", nullable = false)
    private Meta meta;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name = "data_deposito", nullable = false)
    private LocalDateTime dataDeposito = LocalDateTime.now();
}