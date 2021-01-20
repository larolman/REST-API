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

import com.valmeida.begin.api.assembler.CidadeInputDisassembler;
import com.valmeida.begin.api.assembler.CidadeModelAssembler;
import com.valmeida.begin.api.model.CidadeModel;
import com.valmeida.begin.api.model.input.CidadeInput;
import com.valmeida.begin.domain.exception.EstadoNaoEncontradoException;
import com.valmeida.begin.domain.exception.NegocioException;
import com.valmeida.begin.domain.model.Cidade;
import com.valmeida.begin.domain.repository.CidadeRepository;
import com.valmeida.begin.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cidadeService;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@GetMapping
	public List<CidadeModel> listar() {
		return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
	}
	
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@PathVariable Long cidadeId) {
		return cidadeModelAssembler.toModel(cidadeService.buscarOuFalhar(cidadeId));

	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeService.salvar(cidadeInputDisassembler.toDomainObject(cidadeInput));
			return cidadeModelAssembler.toModel(cidade);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}	
	}
	
	@PutMapping("/{cidadeId}")
	public CidadeModel alterar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade CidadeAtual = cidadeService.buscarOuFalhar(cidadeId);
			cidadeInputDisassembler.copyToDomainObject(cidadeInput, CidadeAtual);
			return cidadeModelAssembler.toModel(cidadeService.salvar(CidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}					
	}
	
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cidadeService.remover(cidadeId);

	}
	

}
