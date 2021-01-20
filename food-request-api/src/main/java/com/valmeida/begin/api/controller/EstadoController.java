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

import com.valmeida.begin.api.assembler.EstadoInputDisassembler;
import com.valmeida.begin.api.assembler.EstadoModelAssembler;
import com.valmeida.begin.api.model.EstadoModel;
import com.valmeida.begin.api.model.input.EstadoInput;
import com.valmeida.begin.domain.model.Estado;
import com.valmeida.begin.domain.repository.EstadoRepository;
import com.valmeida.begin.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService estadoService;
	
	@Autowired
	private EstadoModelAssembler estadoModelAssembler;
	
	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;
	
	@GetMapping
	public List<EstadoModel> listar() {
		return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
	}
	
	@GetMapping("/{estadoId}")
	public EstadoModel buscar(@PathVariable Long estadoId) {
		return estadoModelAssembler.toModel(estadoService.buscarOuFalhar(estadoId));
		
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		Estado estado = estadoService.salvar(estadoInputDisassembler.toDomainObject(estadoInput));
		
		return estadoModelAssembler.toModel(estado);
	}
	
	@PutMapping("/{estadoId}")
	public EstadoModel alterar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
		Estado estadoAtual = estadoService.buscarOuFalhar(estadoId);

		estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);
		
		return estadoModelAssembler.toModel(estadoService.salvar(estadoAtual));

	}
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {
		estadoService.remover(estadoId);
	}
}
