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

import com.valmeida.begin.api.assembler.FormaPagamentoInputDisassembler;
import com.valmeida.begin.api.assembler.FormaPagamentoModelAssembler;
import com.valmeida.begin.api.model.FormaPagamentoModel;
import com.valmeida.begin.api.model.input.FormaPagamentoInput;
import com.valmeida.begin.domain.model.FormaPagamento;
import com.valmeida.begin.domain.repository.FormaPagamentoRepository;
import com.valmeida.begin.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/forma-pagamentos")
public class FormaPagamentoController {
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private CadastroFormaPagamentoService formaPagamentoService;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoAssembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;
	
	@GetMapping
	public List<FormaPagamentoModel> listar() throws Exception {
		return formaPagamentoAssembler.toCollectionModel(formaPagamentoRepository.findAll());
	}
	
	@GetMapping("/{formaPagamentoId}")
	public FormaPagamentoModel buscar(@PathVariable Long formaPagamentoId) {
		FormaPagamento formaPagamento = formaPagamentoService.bucarOuFalhar(formaPagamentoId);
		
		return formaPagamentoAssembler.toModel(formaPagamento);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamento = formaPagamentoService
										.salvar(formaPagamentoInputDisassembler
										.toDomainObject(formaPagamentoInput));
		
		return formaPagamentoAssembler.toModel(formaPagamento);
	}
	
	
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(code = HttpStatus.OK)
	public FormaPagamentoModel alterar(@PathVariable Long formaPagamentoId ,@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamentoAtual = formaPagamentoService.bucarOuFalhar(formaPagamentoId);
		
		formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);
		
		return formaPagamentoAssembler.toModel(formaPagamentoService.salvar(formaPagamentoAtual));
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long formaPagamentoId) {
		formaPagamentoService.remover(formaPagamentoId);
	}
	
	
}
