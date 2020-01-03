package com.valmeida.begin.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.valmeida.begin.domain.model.Cozinha;

@Repository
public interface CozinhaRepository  extends JpaRepository<Cozinha, Long>{
	

	
}
