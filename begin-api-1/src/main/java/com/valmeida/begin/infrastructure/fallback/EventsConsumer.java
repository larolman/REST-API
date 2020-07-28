package com.valmeida.begin.infrastructure.fallback;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valmeida.begin.api.assembler.RestauranteInputDisassembler;
import com.valmeida.begin.api.model.input.RestauranteInput;
import com.valmeida.begin.domain.model.Restaurante;
import com.valmeida.begin.domain.service.CadastroRestauranteService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EventsConsumer {
	
	private final String queueName = "food-request-events";
	
	@Autowired
	private RestauranteInputDisassembler disassembler;
	
	@Autowired
	private CadastroRestauranteService service;
	
	@RabbitListener(queues = queueName)
	public void handleMessage(RestauranteInput input) {
		Restaurante restaurante = disassembler.toDomainObject(input);
		service.salvar(restaurante);
		log.info(String.format("Restaurante %s salvo com sucesso!", restaurante.getNome()));
		
	}
}
