package com.icaro.poupanca_metas.service;

import com.icaro.poupanca_metas.entity.Meta;
import com.icaro.poupanca_metas.entity.MetaProgresso;
import com.icaro.poupanca_metas.entity.Usuario;
import com.icaro.poupanca_metas.repository.MetaProgressoRepository;
import com.icaro.poupanca_metas.repository.MetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MetaService {

    @Autowired
    private MetaRepository metaRepository;

    @Autowired
    private MetaProgressoRepository metaProgressoRepository;


    public Meta criarMeta(Usuario usuario, String nome, String descricao,
                          BigDecimal valorAlvo, LocalDate dataMeta) {

        if (dataMeta.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data da meta não pode estar no passado");
        }

        Meta meta = new Meta();
        meta.setUsuario(usuario);
        meta.setNome(nome);
        meta.setDescricao(descricao);
        meta.setValorAlvo(valorAlvo);
        meta.setValorAtual(BigDecimal.ZERO);
        meta.setDataMeta(dataMeta);
        meta.setConcluida(false);

        return metaRepository.save(meta);
    }


    public List<MetaProgresso> listarComProgresso(Integer usuarioId) {
        return metaProgressoRepository.findByUsuarioId(usuarioId);
    }


    public MetaProgresso buscarProgresso(Integer metaId) {
        return metaProgressoRepository.findById(metaId)
                .orElseThrow(() -> new NoSuchElementException("Meta não encontrada"));
    }


    public void marcarComoConcluida(Integer metaId) {
        Meta meta = metaRepository.findById(metaId)
                .orElseThrow(() -> new NoSuchElementException("Meta não encontrada"));
        meta.setConcluida(true);
        metaRepository.save(meta);
    }
}