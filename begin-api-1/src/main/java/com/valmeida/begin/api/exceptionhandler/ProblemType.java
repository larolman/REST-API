package com.valmeida.begin.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
	NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensivel");
	
	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
		this.uri = "https://valmeida.com" + path;
		this.title = title;
	}
}
