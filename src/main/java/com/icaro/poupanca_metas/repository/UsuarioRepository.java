package com.icaro.poupanca_metas.repository;

import com.icaro.poupanca_metas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}