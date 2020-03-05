package com.valmeida.begin.api.model.input;

import javax.validation.constraints.Email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInputAtualizaEmail {
	
	@Email
	private String email;
}
