package com.icaro.poupanca_metas.repository;

import com.icaro.poupanca_metas.entity.HistoricoJuros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface HistoricoJurosRepository extends JpaRepository<HistoricoJuros, Integer> {


    @Query(value = "SELECT COALESCE(SUM(h.juros_calculados), 0) FROM historico_juros h " +
            "JOIN meta m ON m.id = h.meta_id " +
            "WHERE m.usuario_id = :usuarioId " +
            "AND DATE_TRUNC('month', h.data_calculo) = DATE_TRUNC('month', CURRENT_DATE)",
            nativeQuery = true)
    BigDecimal somaJurosDoMes(@Param("usuarioId") Integer usuarioId);
}