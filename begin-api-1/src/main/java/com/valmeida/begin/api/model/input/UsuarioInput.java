package com.valmeida.begin.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {
	
	@Email
	private String email;
	
	@NotBlank
	private String senha;
}
