package com.icaro.poupanca_metas.repository;

import com.icaro.poupanca_metas.entity.Meta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MetaRepository extends JpaRepository<Meta, Integer> {

    List<Meta> findByUsuarioId(Integer usuarioId);

    long countByUsuarioIdAndConcluidaTrue(Integer usuarioId);

    long countByUsuarioIdAndConcluidaFalse(Integer usuarioId);

    @Query(value = "SELECT COALESCE(SUM(valor_atual), 0) FROM meta WHERE usuario_id = :usuarioId",
            nativeQuery = true)
    BigDecimal somaSaldoTotal(@Param("usuarioId") Integer usuarioId);


    @Query(value = "SELECT calcular_juros_poupanca(:metaId, :taxaAnual)", nativeQuery = true)
    BigDecimal calcularJuros(@Param("metaId") Integer metaId, @Param("taxaAnual") BigDecimal taxaAnual);
}