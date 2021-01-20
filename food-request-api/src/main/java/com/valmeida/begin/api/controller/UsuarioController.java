package com.valmeida.begin.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.valmeida.begin.api.assembler.UsuarioInputDisassembler;
import com.valmeida.begin.api.assembler.UsuarioModelAssembler;
import com.valmeida.begin.api.model.UsuarioModel;
import com.valmeida.begin.api.model.input.UsuarioInput;
import com.valmeida.begin.api.model.input.UsuarioInputAtualizaEmail;
import com.valmeida.begin.api.model.input.UsuarioInputAtualizaSenha;
import com.valmeida.begin.domain.model.Usuario;
import com.valmeida.begin.domain.repository.UsuarioRepository;
import com.valmeida.begin.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CadastroUsuarioService usuarioService;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;
	
	@GetMapping
	public List<UsuarioModel> listar() {
		return usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll());
	}
	
	@GetMapping("/{usuarioId}")
	public UsuarioModel buscar(@PathVariable Long usuarioId) {
		return usuarioModelAssembler.toModel(usuarioService.buscarOuFalhar(usuarioId));
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public UsuarioModel adicionar(@RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
		
		return usuarioModelAssembler.toModel(usuarioService.salvar(usuario));
	}
	
	@PutMapping("/{usuarioId}")
	public UsuarioModel alterar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInputAtualizaEmail usuarioInput) {
		Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
		
		usuarioInputDisassembler.copyEmailToDomainObject(usuarioInput, usuarioAtual);
		
		return usuarioModelAssembler.toModel(usuarioService.salvar(usuarioAtual));
	}
	
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInputAtualizaSenha usuarioInput) {
		usuarioService.alterarSenha(usuarioId, usuarioInput);
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long usuarioId) {
		usuarioService.remover(usuarioId);
	}
}
