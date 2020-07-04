package com.valmeida.begin.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.valmeida.begin.api.assembler.ProdutoInputDisassembler;
import com.valmeida.begin.api.assembler.ProdutoModelAssembler;
import com.valmeida.begin.api.model.ProdutoModel;
import com.valmeida.begin.api.model.input.ProdutoInput;
import com.valmeida.begin.domain.model.Produto;
import com.valmeida.begin.domain.model.Restaurante;
import com.valmeida.begin.domain.service.CadastroProdutoService;
import com.valmeida.begin.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
	
	@Autowired
	private CadastroProdutoService produtoService;
	
	@Autowired
	private CadastroRestauranteService restauranteService;
	
	@Autowired
	private ProdutoInputDisassembler inputDisassembler;
	
	@Autowired
	private ProdutoModelAssembler modelAssembler;
	
	@GetMapping
	public List<ProdutoModel> listar(@PathVariable Long restauranteId) {
		
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		return modelAssembler.toCollectionModel(restaurante.getProdutos());
	}
	
	@GetMapping("/{produtoId}")
	public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		Produto produto = produtoService.buscarOuFalhar(produtoId, restaurante);
		
		return modelAssembler.toModel(produto);	
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
		Produto produto = inputDisassembler.toDomainObject(produtoInput);
				
		return modelAssembler.toModel(produtoService.salvar(produto, restauranteId));
	}
	
	@PutMapping("/{produtoId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ProdutoModel alterar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@RequestBody @Valid ProdutoInput produtoInput) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		Produto produtoAtual = produtoService.buscarOuFalhar(produtoId, restaurante);
		inputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
		
		return modelAssembler.toModel(produtoService.salvar(produtoAtual, restauranteId));
	}
	
	
}
