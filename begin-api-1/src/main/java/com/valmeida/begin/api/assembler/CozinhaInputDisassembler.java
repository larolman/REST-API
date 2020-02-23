package com.valmeida.begin.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.valmeida.begin.api.model.input.CozinhaInput;
import com.valmeida.begin.domain.model.Cozinha;

@Component
public class CozinhaInputDisassembler {
	
	private ModelMapper modelMapper;
	
	public Cozinha toDomainObject(CozinhaInput cozinhaInput) {
		return modelMapper.map(cozinhaInput, Cozinha.class);
	}
	
	public void copyToDomainObject(CozinhaInput cozinhainput, Cozinha cozinha) {
		modelMapper.map(cozinhainput, cozinha);
	}
}
