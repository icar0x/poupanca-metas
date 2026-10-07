package com.icaro.poupanca_metas.service;

import com.icaro.poupanca_metas.repository.DepositoRepository;
import com.icaro.poupanca_metas.repository.HistoricoJurosRepository;
import com.icaro.poupanca_metas.repository.MetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelatorioService {

    @Autowired
    private DepositoRepository depositoRepository;

    @Autowired
    private HistoricoJurosRepository historicoJurosRepository;

    @Autowired
    private MetaRepository metaRepository;

    // RF08/RN07: relatório financeiro consolidado do mês corrente
    public RelatorioMensal gerarRelatorioMensal(Integer usuarioId) {
        return new RelatorioMensal(
                depositoRepository.somaDepositosDoMes(usuarioId),
                historicoJurosRepository.somaJurosDoMes(usuarioId),
                metaRepository.somaSaldoTotal(usuarioId),
                metaRepository.countByUsuarioIdAndConcluidaTrue(usuarioId),
                metaRepository.countByUsuarioIdAndConcluidaFalse(usuarioId)
        );
    }
}