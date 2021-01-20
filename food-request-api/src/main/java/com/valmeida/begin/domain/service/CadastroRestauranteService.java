package com.valmeida.begin.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valmeida.begin.domain.exception.EntidadeEmUsoException;
import com.valmeida.begin.domain.exception.RestauranteNaoEncontradoException;
import com.valmeida.begin.domain.model.Cidade;
import com.valmeida.begin.domain.model.Cozinha;
import com.valmeida.begin.domain.model.FormaPagamento;
import com.valmeida.begin.domain.model.Restaurante;
import com.valmeida.begin.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	private static final String MSG_ENTIDADE_EM_USO = "Restaurante de código %d não pode ser removido pois está em uso";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cozinhaService;
	
	@Autowired
	private CadastroCidadeService cidadeService;
	
	@Autowired
	private CadastroFormaPagamentoService formaPagamentoService;
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
		Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);
		
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);
		
		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.ativar();
		
	}
	
	@Transactional
	public void inativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.inativar();
		
	}
	
	@Transactional
	public void abertura(Long restauranteId) {
		final var restaurante = buscarOuFalhar(restauranteId);
		restaurante.setAberto(true);
	}

	@Transactional
	public void fechamento(Long restauranteId) {
		final var restaurante = buscarOuFalhar(restauranteId);
		restaurante.setAberto(false);
	}

	@Transactional
	public void remover(Long restauranteId) {
		try {
			restauranteRepository.deleteById(restauranteId);
			restauranteRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(restauranteId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ENTIDADE_EM_USO, restauranteId));		
		}
		
	}
	
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.bucarOuFalhar(formaPagamentoId);
		
		restaurante.removerFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void asassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.bucarOuFalhar(formaPagamentoId);
		
		restaurante.adicionarFormaPagamento(formaPagamento);
	}

	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
}
