package com.icaro.poupanca_metas.repository;

import com.icaro.poupanca_metas.entity.MetaProgresso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MetaProgressoRepository extends JpaRepository<MetaProgresso, Integer> {

    List<MetaProgresso> findByUsuarioId(Integer usuarioId);
}