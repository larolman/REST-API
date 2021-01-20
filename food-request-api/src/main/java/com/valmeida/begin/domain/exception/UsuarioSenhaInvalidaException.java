package com.valmeida.begin.domain.exception;

public class UsuarioSenhaInvalidaException extends NegocioException {
	
	private static final long serialVersionUID = 1L;
	
	public UsuarioSenhaInvalidaException(String mensagem) {
		super(mensagem);
	}

}
