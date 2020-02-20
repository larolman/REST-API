package com.valmeida.begin.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.valmeida.begin.domain.model.Cidade;

public abstract class EnderecoMixin {
	
	@JsonIgnore
	private Cidade cidade;
}
