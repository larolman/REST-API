package com.valmeida.begin.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.valmeida.begin.domain.model.Estado;
import com.valmeida.begin.domain.repository.EstadoRepository;

@Component
public class EstadoRepositoryImpl implements EstadoRepository{
	
	@PersistenceContext
	EntityManager manager;
	
	@Override
	@Transactional
	public Estado salvar(Estado estado) {
		
		return manager.merge(estado);
	}

	@Override
	public Estado buscar(Long id) {
		
		return manager.find(Estado.class, id);
	}

	@Override
	public List<Estado> listar() {
		
		return manager.createQuery("from Estado", Estado.class).getResultList();
	}

	@Override
	@Transactional
	public void remover(Long id) {
		
		Estado estado = buscar(id);
		
		if (estado == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		manager.remove(estado);
	}
	
	

}
