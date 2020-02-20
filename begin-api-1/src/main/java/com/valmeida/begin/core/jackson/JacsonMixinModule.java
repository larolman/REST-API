package com.valmeida.begin.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.valmeida.begin.api.model.mixin.CozinhaMixin;
import com.valmeida.begin.api.model.mixin.EnderecoMixin;
import com.valmeida.begin.domain.model.Cozinha;
import com.valmeida.begin.domain.model.Endereco;

@Component
public class JacsonMixinModule extends SimpleModule{

	private static final long serialVersionUID = 1L;
	
	public JacsonMixinModule() {
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
		setMixInAnnotation(Endereco.class, EnderecoMixin.class);
	}
}
