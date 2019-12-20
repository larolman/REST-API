package com.valmeida.begin.domain.repository;

import java.util.List;

import com.valmeida.begin.domain.model.Cidade;

public interface CidadeRepository {
	
	Cidade salvar(Cidade cidade);
	Cidade buscar(Long id);
	void remover(Long id);
	List<Cidade> listar();
	

}
