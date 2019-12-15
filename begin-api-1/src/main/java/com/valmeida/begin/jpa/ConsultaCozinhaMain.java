package com.valmeida.begin.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import com.valmeida.begin.BeginApi1Application;
import com.valmeida.begin.domain.model.Cozinha;

public class ConsultaCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext app = new SpringApplicationBuilder(BeginApi1Application.class).web(WebApplicationType.NONE)
				.run(args);
		
		CadastroCozinha cadastrocozinha = app.getBean(CadastroCozinha.class);
		
		Cozinha brasileira = new Cozinha();
		brasileira.setNome("Brasileira");
		
		Cozinha japonesa = new Cozinha();
		japonesa.setNome("Japonesa");
		
		brasileira = cadastrocozinha.adicionar(brasileira);
		japonesa = cadastrocozinha.adicionar(japonesa);
		
		System.out.printf("%d - %s\n", brasileira.getId(), brasileira.getNome());
		System.out.printf("%d - %s\n", japonesa.getId(), japonesa.getNome());
		
		Cozinha cozinha = cadastrocozinha.buscar(1L);
		
		System.out.printf("%d - %s\n", cozinha.getId(), cozinha.getNome());
		
	}

}
