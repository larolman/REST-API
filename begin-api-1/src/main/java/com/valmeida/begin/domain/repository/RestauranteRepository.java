package com.valmeida.begin.domain.repository;

import java.util.List;

import com.valmeida.begin.domain.model.Restaurante;

public interface RestauranteRepository {
	Restaurante salvar(Restaurante restaurante);
	Restaurante buscar(Long id);
	void remover(Restaurante restaurante);
	List<Restaurante> listar();
}
