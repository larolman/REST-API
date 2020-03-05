package com.valmeida.begin.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.valmeida.begin.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
