package com.valmeida.begin.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valmeida.begin.api.model.input.UsuarioInput;
import com.valmeida.begin.api.model.input.UsuarioInputAtualizaEmail;
import com.valmeida.begin.domain.model.Usuario;

@Component
public class UsuarioInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario toDomainObject(UsuarioInput usuarioInput) {
		return modelMapper.map(usuarioInput, Usuario.class);
	}
	
	public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
		modelMapper.map(usuarioInput, usuario);
	}
	
	public void copyEmailToDomainObject(UsuarioInputAtualizaEmail usuarioInput, Usuario usuario) {
		modelMapper.map(usuarioInput, usuario);
	}
}
