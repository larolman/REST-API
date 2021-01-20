package com.valmeida.begin.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valmeida.begin.api.model.input.UsuarioInputAtualizaSenha;
import com.valmeida.begin.domain.exception.EntidadeEmUsoException;
import com.valmeida.begin.domain.exception.NegocioException;
import com.valmeida.begin.domain.exception.UsuarioNaoEncontradoException;
import com.valmeida.begin.domain.exception.UsuarioSenhaInvalidaException;
import com.valmeida.begin.domain.model.Usuario;
import com.valmeida.begin.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {
	
	private static final String MSG_ENTIDADE_EM_USO = "Usuário de código %d não pode ser removido pois está em uso";
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(
					String.format("Já existe usuário com o e-mail %s", usuario.getEmail()));
		}
		
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void remover(Long usuarioId) {
		try {
			usuarioRepository.deleteById(usuarioId);
		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(usuarioId);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ENTIDADE_EM_USO, usuarioId));
		}
	}
	
	public Usuario buscarOuFalhar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId)
								.orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}
	
	@Transactional
	public void alterarSenha(Long usuarioId, UsuarioInputAtualizaSenha usuarioInput) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		
		if (usuario.getSenha().equals(usuarioInput.getSenhaAtual())) {
			usuario.setSenha(usuarioInput.getNovaSenha());
		} else {
			throw new UsuarioSenhaInvalidaException("Senha atual inválida, tente novamente");
		}
	}
}
