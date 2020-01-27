package com.valmeida.begin.domain.exception;

public class RestauranteNaoEncontradoException extends NegocioException{

	private static final long serialVersionUID = 1L;
	
	public RestauranteNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public RestauranteNaoEncontradoException(Long restauranteId) {
		this(String.format("Restaurante com id %d não encontrado", restauranteId));
	}

}
