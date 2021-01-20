package com.valmeida.begin.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valmeida.begin.api.model.input.CozinhaInput;
import com.valmeida.begin.domain.model.Cozinha;

@Service
public class CozinhaInputDisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public Cozinha toDomainObject(CozinhaInput cozinhaInput) {
		return modelMapper.map(cozinhaInput, Cozinha.class);
	}
	
	public void copyToDomainObject(CozinhaInput cozinhainput, Cozinha cozinha) {
		modelMapper.map(cozinhainput, cozinha);
	}
}
