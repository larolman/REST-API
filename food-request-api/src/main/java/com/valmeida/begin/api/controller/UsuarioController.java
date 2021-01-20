package com.valmeida.begin.api.controller;

import com.valmeida.begin.api.assembler.GrupoModelAssembler;
import com.valmeida.begin.api.assembler.UsuarioInputDisassembler;
import com.valmeida.begin.api.assembler.UsuarioModelAssembler;
import com.valmeida.begin.api.model.GrupoModel;
import com.valmeida.begin.api.model.UsuarioModel;
import com.valmeida.begin.api.model.input.UsuarioInput;
import com.valmeida.begin.api.model.input.UsuarioInputAtualizaEmail;
import com.valmeida.begin.api.model.input.UsuarioInputAtualizaSenha;
import com.valmeida.begin.domain.model.Usuario;
import com.valmeida.begin.domain.repository.UsuarioRepository;
import com.valmeida.begin.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@GetMapping
	public List<UsuarioModel> listar() {
		return usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll());
	}
	
	@GetMapping("/{usuarioId}")
	public UsuarioModel buscar(@PathVariable Long usuarioId) {
		return usuarioModelAssembler.toModel(usuarioService.buscarOuFalhar(usuarioId));
	}

	@GetMapping("/{usuarioId}/grupos")
	public List<GrupoModel> listarGrupos(@PathVariable final Long usuarioId) {
		final var usuario = this.usuarioService.buscarOuFalhar(usuarioId);

		return this.grupoModelAssembler.toCollectionModel(usuario.getGrupos());
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

	@PutMapping("/{usuarioId}/grupos/{grupoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void associarGrupo(@PathVariable final Long usuarioId, @PathVariable final Long grupoId) {
		this.usuarioService.associarGrupo(usuarioId, grupoId);
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

	@DeleteMapping("{usuarioId}/grupos/{grupoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void desassociarGrupo(@PathVariable final Long usuarioId, @PathVariable final Long grupoId) {
		this.usuarioService.desassociarGrupo(usuarioId,grupoId);
	}
}
