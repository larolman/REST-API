package com.valmeida.begin.domain.repository;

import java.util.List;

import com.valmeida.begin.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {
	
	FormaPagamento buscar(Long id);
	FormaPagamento salvar(FormaPagamento formaPagamento);
	void remover(FormaPagamento formaPagamento);
	List<FormaPagamento> listar();

}