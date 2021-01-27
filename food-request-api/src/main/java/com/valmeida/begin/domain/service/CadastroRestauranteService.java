package com.valmeida.begin.domain.service;


import com.valmeida.begin.domain.dto.RestauranteAvroMapper;
import com.valmeida.begin.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valmeida.begin.domain.exception.EntidadeEmUsoException;
import com.valmeida.begin.domain.exception.RestauranteNaoEncontradoException;
import com.valmeida.begin.domain.repository.RestauranteRepository;
import com.valmeida.begin.infrastructure.kafka.producer.RestauranteProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Autowired
	private CadastroUsuarioService usuarioService;

	@Autowired
	private RestauranteProducer restauranteProducer;

	@Autowired
	private RestauranteAvroMapper mapper;

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
		Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);
		
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);

		restaurante = restauranteRepository.save(restaurante);
		this.restauranteEvent(restaurante);

		return restaurante;
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

	@Transactional
	public void associarUsuarioResponsavel(final Long restauranteId, final Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = this.usuarioService.buscarOuFalhar(usuarioId);

		restaurante.associonarUsuarioResponsavel(usuario);
	}

	@Transactional
	public void desassociarUsuarioResponsavel(final Long restauranteId, final Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = this.usuarioService.buscarOuFalhar(usuarioId);

		restaurante.desassociarUsuarioResponsavel(usuario);
	}

	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}

	private void restauranteEvent(final Restaurante restaurante) {
		this.restauranteProducer.send(this.mapper.toAvro(restaurante));
	}
}