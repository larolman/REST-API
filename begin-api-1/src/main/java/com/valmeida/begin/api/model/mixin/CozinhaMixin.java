package com.valmeida.begin.api.model.mixin;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.valmeida.begin.domain.model.Restaurante;

public abstract class CozinhaMixin {
	
	@JsonIgnore
	@OneToMany(mappedBy = "cozinha")
	private List<Restaurante> restaurantes = new ArrayList<>();
}
