package com.valmeida.begin.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valmeida.begin.domain.exception.ProdutoNaoEncontradoException;
import com.valmeida.begin.domain.model.Produto;
import com.valmeida.begin.domain.model.Restaurante;
import com.valmeida.begin.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CadastroRestauranteService restauranteService;
	
	@Transactional
	public Produto salvar(Produto produto, Long restauranteId) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		produto.setRestaurante(restaurante);
		
		return produtoRepository.save(produto);
	}
	
	//Revisar
	@Transactional
	public void remover(Long produtoId) {
		try {
			produtoRepository.deleteById(produtoId);
		} catch (EmptyResultDataAccessException e) {
			throw new ProdutoNaoEncontradoException(produtoId);
		}
	}
	
	public Produto buscarOuFalhar(Long produtoId, Restaurante restaurante) {
		return produtoRepository.findByIdAndRestaurante(produtoId, restaurante)
				.orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
	}
	
}