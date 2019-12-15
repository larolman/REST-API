package com.valmeida.begin.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.valmeida.begin.BeginApi1Application;
import com.valmeida.begin.domain.model.Cozinha;

public class InclusaoCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext app = new SpringApplicationBuilder(BeginApi1Application.class).web(WebApplicationType.NONE)
				.run(args);
		
		CadastroCozinha cadastrocozinha = app.getBean(CadastroCozinha.class);
		
		List<Cozinha> cozinhas = cadastrocozinha.listar();
		
		cozinhas.forEach(c -> System.out.println("Cozinha tipo: " + c.getNome()));

	}

}
