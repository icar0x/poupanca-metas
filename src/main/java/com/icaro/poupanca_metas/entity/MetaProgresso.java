package com.icaro.poupanca_metas.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Immutable
@Table(name = "vw_metas_com_progresso")
@Getter
public class MetaProgresso {

    @Id
    @Column(name = "meta_id")
    private Integer metaId;

    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "nome_meta")
    private String nomeMeta;

    @Column(name = "valor_alvo")
    private BigDecimal valorAlvo;

    @Column(name = "valor_atual")
    private BigDecimal valorAtual;

    @Column(name = "percentual_progresso")
    private BigDecimal percentualProgresso;

    @Column(name = "dias_restantes")
    private Integer diasRestantes;

    @Column(name = "status")
    private String status;
}