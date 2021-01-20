package com.valmeida.begin.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.valmeida.begin.domain.model.Produto;
import com.valmeida.begin.domain.model.Restaurante;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	Optional<Produto> findByIdAndRestaurante(Long produtoId, Restaurante restaurante);
	
}
