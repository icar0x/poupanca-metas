package com.icaro.poupanca_metas.service;

import java.math.BigDecimal;

public record RelatorioMensal(
        BigDecimal totalDepositado,
        BigDecimal totalJuros,
        BigDecimal saldoTotal,
        long metasConcluidas,
        long metasEmProgresso
) {
}