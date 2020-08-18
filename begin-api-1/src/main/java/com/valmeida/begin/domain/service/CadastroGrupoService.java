package com.valmeida.begin.domain.service;

import com.valmeida.begin.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valmeida.begin.domain.exception.EntidadeEmUsoException;
import com.valmeida.begin.domain.exception.GrupoNaoEncontradoException;
import com.valmeida.begin.domain.model.Grupo;
import com.valmeida.begin.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {
	
	private static final String MSG_ENTIDADE_EM_USO = "Grupo de código %d não pode ser removido pois está em uso";
	
	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private CadastroPermissaoService permissaoService;
	
	@Transactional
	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}
	
	@Transactional
	public void remover(Long grupoId) {
		try {
			grupoRepository.deleteById(grupoId);
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(grupoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ENTIDADE_EM_USO, grupoId));
		}
	}

	@Transactional
	public void adicionarPermissao(final Long grupoId, final Long permissaoId) {
		final var grupo = this.buscarOuFalhar(grupoId);
		final var permissao = this.permissaoService.buscarOuFalhar(permissaoId);

		grupo.adicionarPermissao(permissao);
	}

	@Transactional
	public void removerPermissao(final Long grupoId, final Long permissaoId) {
		final var grupo = this.buscarOuFalhar(grupoId);
		final var permissao = this.permissaoService.buscarOuFalhar(permissaoId);

		grupo.removerPermissao(permissao);
	}
	
	public Grupo buscarOuFalhar(Long grupoId) {
		return grupoRepository.findById(grupoId)
								.orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
	}
}
