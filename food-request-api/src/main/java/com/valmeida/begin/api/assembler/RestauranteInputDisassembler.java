package com.valmeida.begin.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valmeida.begin.api.model.input.RestauranteInput;
import com.valmeida.begin.domain.model.Cidade;
import com.valmeida.begin.domain.model.Cozinha;
import com.valmeida.begin.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);	
	}
	
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		// Para evitar Hibernate Exception
		restaurante.setCozinha(new Cozinha());
		
		restaurante.getEndereco().setCidade(new Cidade());
		
		modelMapper.map(restauranteInput, restaurante);
	}
}
