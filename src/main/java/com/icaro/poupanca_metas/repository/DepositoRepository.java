package com.icaro.poupanca_metas.repository;

import com.icaro.poupanca_metas.entity.Deposito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface DepositoRepository extends JpaRepository<Deposito, Integer> {

    List<Deposito> findByMetaId(Integer metaId);

    // RF08/RN07: soma dos depósitos do usuário no mês corrente
    @Query(value = "SELECT COALESCE(SUM(d.valor), 0) FROM deposito d " +
            "JOIN meta m ON m.id = d.meta_id " +
            "WHERE m.usuario_id = :usuarioId " +
            "AND DATE_TRUNC('month', d.data_deposito) = DATE_TRUNC('month', CURRENT_DATE)",
            nativeQuery = true)
    BigDecimal somaDepositosDoMes(@Param("usuarioId") Integer usuarioId);
}