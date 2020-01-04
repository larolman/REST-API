package com.valmeida.begin.domain.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.valmeida.begin.domain.model.Cozinha;

@Repository
public interface CozinhaRepository  extends JpaRepository<Cozinha, Long>{

	List<Cozinha> findByNomeContaining(String nome);
	
	boolean existsByNome(String nome);

	
}