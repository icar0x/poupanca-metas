package com.icaro.poupanca_metas.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class DepositoService {

    @PersistenceContext
    private EntityManager entityManager;

    // RF04: registrar depósito em uma meta.
    // Chama a PROCEDURE registrar_deposito_em_meta, que já valida
    // RN02 (valor > 0), RN03 (meta não concluída) e aplica RN06
    // (conclusão automática) direto no banco.
    @Transactional
    public void registrarDeposito(Integer metaId, BigDecimal valor) {
        entityManager.createNativeQuery("CALL registrar_deposito_em_meta(:metaId, :valor)")
                .setParameter("metaId", metaId)
                .setParameter("valor", valor)
                .executeUpdate();
    }
}