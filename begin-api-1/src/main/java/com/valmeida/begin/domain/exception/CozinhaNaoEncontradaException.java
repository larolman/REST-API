package com.valmeida.begin.domain.exception;

public class CozinhaNaoEncontradaException extends NegocioException{

	private static final long serialVersionUID = 1L;
	
	public CozinhaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CozinhaNaoEncontradaException(Long cozinhaId) {
		this(String.format("Cozinha com id %d não encontrada", cozinhaId));
	}

}
