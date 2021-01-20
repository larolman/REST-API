package com.valmeida.begin.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valmeida.begin.domain.exception.EntidadeEmUsoException;
import com.valmeida.begin.domain.exception.FormaPagamentoNaoEncontradaException;
import com.valmeida.begin.domain.model.FormaPagamento;
import com.valmeida.begin.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {
	
	private static final String MSG_ENTIDADE_EM_USO = "Forma de pagamento de código %d não pode ser removido pois está em uso";
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	public void remover(Long formaPagamentoId) {
		try {
			formaPagamentoRepository.deleteById(formaPagamentoId);
//			formaPagamentoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradaException(formaPagamentoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ENTIDADE_EM_USO, formaPagamentoId));
		}
	}
	
	public FormaPagamento bucarOuFalhar(Long formaPagamentoId) {
		return formaPagamentoRepository.findById(formaPagamentoId)
										.orElseThrow(() -> new FormaPagamentoNaoEncontradaException(formaPagamentoId));
	}

}
