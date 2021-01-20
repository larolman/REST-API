package com.valmeida.begin.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valmeida.begin.domain.exception.CidadeNaoEncontradaException;
import com.valmeida.begin.domain.exception.EntidadeEmUsoException;

import com.valmeida.begin.domain.model.Cidade;
import com.valmeida.begin.domain.model.Estado;
import com.valmeida.begin.domain.repository.CidadeRepository;



@Service
public class CadastroCidadeService {
	
	private static final String MSG_ENTIDADE_EM_USO = "Cidade de código %d não pode ser removido pois está em uso";

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService estadoService;
	
	@Transactional
	public Cidade salvar(Cidade cidade) {
		Long estadoId =  cidade.getEstado().getId();
		Estado estado = estadoService.buscarOuFalhar(estadoId);

		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}
	
	@Transactional
	public void remover(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
			cidadeRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(cidadeId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ENTIDADE_EM_USO, cidadeId));		
		}
	}

	public Cidade buscarOuFalhar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
				.orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
	}
}
