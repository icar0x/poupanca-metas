package com.icaro.poupanca_metas.service;

import com.icaro.poupanca_metas.entity.HistoricoJuros;
import com.icaro.poupanca_metas.entity.Meta;
import com.icaro.poupanca_metas.repository.HistoricoJurosRepository;
import com.icaro.poupanca_metas.repository.MetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
public class JurosService {

    @Autowired
    private MetaRepository metaRepository;

    @Autowired
    private HistoricoJurosRepository historicoJurosRepository;

    // RF06: calcular juros (chama a FUNCTION calcular_juros_poupanca)
    // e aplicar o valor ao saldo da meta, registrando no histórico.
    @Transactional
    public BigDecimal calcularEAplicarJuros(Integer metaId, BigDecimal taxaAnual) {
        Meta meta = metaRepository.findById(metaId)
                .orElseThrow(() -> new NoSuchElementException("Meta não encontrada"));

        BigDecimal juros = metaRepository.calcularJuros(metaId, taxaAnual);

        meta.setValorAtual(meta.getValorAtual().add(juros));
        metaRepository.save(meta);

        HistoricoJuros historico = new HistoricoJuros();
        historico.setMeta(meta);
        historico.setJurosCalculados(juros);
        historico.setSaldoAposJuros(meta.getValorAtual());
        historicoJurosRepository.save(historico);

        return juros;
    }
}