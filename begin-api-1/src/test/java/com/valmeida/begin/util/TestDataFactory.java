package com.valmeida.begin.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valmeida.begin.domain.model.Cozinha;
import com.valmeida.begin.domain.model.Restaurante;
import com.valmeida.begin.domain.repository.CozinhaRepository;
import com.valmeida.begin.domain.repository.RestauranteRepository;

@Component
public class TestDataFactory {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	public List<Cozinha> cozinhas = new ArrayList<>();
	
	public List<Restaurante> restaurantes = new ArrayList<>();
	
	public final int RESOURCE_INEXISTENTE = 100;
	
	private static final TestDataFactory INSTANCE = new TestDataFactory();
	
	private TestDataFactory() {}
	
	public static TestDataFactory getInstance() {return INSTANCE;}
	
	
	public void adicionaCozinhas() {
		Cozinha indiana = new Cozinha();
		indiana.setNome("Indiana");
		cozinhaRepository.save(indiana);
		
		Cozinha tailandesa = new Cozinha();
		tailandesa.setNome("Tailandesa");
		cozinhaRepository.save(tailandesa);
		
		cozinhas.add(tailandesa);
		cozinhas.add(indiana);
	}
	
	public void adicionaRestaurantes() {
		adicionaCozinhas();
		
		Restaurante thaiDelivery = new Restaurante();
		thaiDelivery.setNome("Thai Delivery");
		thaiDelivery.setTaxaFrete(new BigDecimal("9.50"));
		thaiDelivery.setCozinha(cozinhas.get(0));
		restauranteRepository.save(thaiDelivery);
		
		Restaurante tukTuk = new Restaurante();
		tukTuk.setNome("Tuk Tuk Comida Indiana");
		tukTuk.setTaxaFrete(new BigDecimal("12"));
		tukTuk.setCozinha(cozinhas.get(1));
		restauranteRepository.save(tukTuk);
		
		restaurantes.add(thaiDelivery);
		restaurantes.add(tukTuk);
	}
}
