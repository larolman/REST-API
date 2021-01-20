package com.valmeida.begin.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.valmeida.begin.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	
	List<Restaurante> findComFreteGratis(String nome);

}