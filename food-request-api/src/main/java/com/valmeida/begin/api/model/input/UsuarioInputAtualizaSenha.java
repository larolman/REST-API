package com.valmeida.begin.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInputAtualizaSenha {
	
	@NotBlank
	private String senhaAtual;
	
	@NotBlank
	private String novaSenha;
}
