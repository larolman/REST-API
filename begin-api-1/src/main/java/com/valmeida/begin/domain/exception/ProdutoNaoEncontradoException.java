package com.valmeida.begin.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public ProdutoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public ProdutoNaoEncontradoException(Long produtoId) {
		this(String.format("Produto com id %d não encontrado", produtoId));
	}
}
