package com.valmeida.begin.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valmeida.begin.domain.exception.EntidadeNaoEncontradaException;
import com.valmeida.begin.domain.model.Cozinha;
import com.valmeida.begin.domain.model.Restaurante;
import com.valmeida.begin.domain.repository.CozinhaRepository;
import com.valmeida.begin.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe cozinha com Id %d", cozinhaId)));
		
		restaurante.setCozinha(cozinha);
		
		return restauranteRepository.salvar(restaurante);
	}
	
}
