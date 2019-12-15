package com.valmeida.begin.domain.repository;

import java.util.List;
import com.valmeida.begin.domain.model.Estado;

public interface EstadoRepository {
	
	Estado salvar(Estado estado);
	Estado buscar(Long id);
	List<Estado> listar();
	

}
