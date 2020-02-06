package com.valmeida.begin.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	NEGOCIO("/erro-negocio", "Violação de regra de negócio."),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso."),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensivel."),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parametro inválido."),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado."),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema.");
	
	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
		this.uri = "https://valmeida.com" + path;
		this.title = title;
	}
}
