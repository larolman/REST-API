package com.valmeida.begin.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.valmeida.begin.api.assembler.PermissaoModelAssembler;
import com.valmeida.begin.api.model.PermissaoModel;
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

import com.valmeida.begin.api.assembler.GrupoInputDisassembler;
import com.valmeida.begin.api.assembler.GrupoModelAssembler;
import com.valmeida.begin.api.model.GrupoModel;
import com.valmeida.begin.api.model.input.GrupoInput;
import com.valmeida.begin.domain.model.Grupo;
import com.valmeida.begin.domain.repository.GrupoRepository;
import com.valmeida.begin.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	private CadastroGrupoService grupoService;

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;

	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;
	
	@GetMapping
	public List<GrupoModel> listar() {
		return grupoModelAssembler.toCollectionModel(grupoRepository.findAll());
	}
	
	@GetMapping("/{grupoId}")
	public GrupoModel buscar(@PathVariable Long grupoId) {
		return grupoModelAssembler.toModel(grupoService.buscarOuFalhar(grupoId));
	}

	@GetMapping("/{grupoId}/permissoes")
	public List<PermissaoModel> listarPermissoes(@PathVariable final Long grupoId) {
		final var grupo = this.grupoService.buscarOuFalhar(grupoId);

		return this.permissaoModelAssembler.toCollectionModel(grupo.getPermissoes());
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupo = grupoService.salvar(grupoInputDisassembler.toDomainObject(grupoInput));
		
		return grupoModelAssembler.toModel(grupo);
	}

	@PutMapping("/{grupoId}/permissoes/{permissaoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void adicionarPermissao(@PathVariable final Long grupoId, @PathVariable final Long permissaoId) {
		this.grupoService.adicionarPermissao(grupoId, permissaoId);
	}
	
	@PutMapping("/{grupoId}")
	public GrupoModel alterar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupoAtual = grupoService.buscarOuFalhar(grupoId);
		
		grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);
		
		return grupoModelAssembler.toModel(grupoService.salvar(grupoAtual));
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long grupoId) {
		grupoService.remover(grupoId);
	}

	@DeleteMapping("/{grupoId}/permissoes/{permissaoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removerPermissao(@PathVariable final Long grupoId, @PathVariable final Long permissaoId) {
		this.grupoService.removerPermissao(grupoId, permissaoId);
	}
}
