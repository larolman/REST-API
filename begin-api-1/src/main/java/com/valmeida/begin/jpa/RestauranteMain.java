package com.valmeida.begin.jpa;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import com.valmeida.begin.BeginApi1Application;
import com.valmeida.begin.domain.model.Restaurante;
import com.valmeida.begin.domain.repository.RestauranteRepository;
import com.valmeida.begin.infrastructure.repository.RestauranteRepositoryImpl;

public class RestauranteMain {

	public static void main(String[] args) {
		ApplicationContext app = new SpringApplicationBuilder(BeginApi1Application.class).web(WebApplicationType.NONE)
				.run(args);
		
		RestauranteRepository rr = app.getBean(RestauranteRepositoryImpl.class);
		
//		Restaurante restaurante = new Restaurante();
//		restaurante.setNome("Burguer King");
//		restaurante.setTaxaFrete(new BigDecimal(7));
//		
//		
//		restaurante = rr.salvar(restaurante);
//		
//		System.out.printf("ID: %d - Restaurante: %s \nTaxa de Entrega: %.2f", restaurante.getId() ,restaurante.getNome(), 
//				restaurante.getTaxaFrete());
		
//		Restaurante restaurante2 = rr.buscar(2L);
		
//		System.out.printf("ID: %d - Restaurante: %s \nTaxa de Entrega: %.2f", restaurante2.getId() ,restaurante2.getNome(), 
//				restaurante2.getTaxaFrete());
		
		List<Restaurante> restaurantes = rr.listar();
		
		restaurantes.forEach(r -> System.out.printf("ID: %d - Restaurante: %s \nTaxa de Entrega: %.2f\nCozinha: %s\n", r.getId() ,r.getNome(), 
				r.getTaxaFrete(), r.getCozinha().getNome() ));
		
	}

}
