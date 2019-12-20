package com.valmeida.begin.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valmeida.begin.domain.exception.EntidadeNaoEncontradaException;
import com.valmeida.begin.domain.model.Restaurante;
import com.valmeida.begin.domain.repository.RestauranteRepository;
import com.valmeida.begin.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService restauranteService;
	
	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.listar();
	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
		
		 Restaurante restaurante = restauranteRepository.buscar(restauranteId);
		 
		 if (restaurante != null) {
			 return ResponseEntity.ok().body(restaurante);
		 }
		 
		 return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		
		try {
			restaurante = restauranteService.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
			}
	}
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> alterar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante){
		Restaurante buscarRestaurante = restauranteRepository.buscar(restauranteId);
		
		if (buscarRestaurante == null) {
			return ResponseEntity.notFound().build();
		}
		
		try {
			restaurante.setId(restauranteId);
			restaurante = restauranteService.salvar(restaurante);
			return ResponseEntity.ok(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, 
												@RequestBody Map<String, Object> campos) {
		
		Restaurante buscarRestaurante = restauranteRepository.buscar(restauranteId);
		
		if (buscarRestaurante == null) {
			return ResponseEntity.notFound().build();
		}
		
		merge(campos, buscarRestaurante);
		
		return alterar(restauranteId, buscarRestaurante);
	}

	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);

		camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
	
}
