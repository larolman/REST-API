package com.valmeida.begin.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.valmeida.begin.domain.model.Cidade;
import com.valmeida.begin.domain.repository.CidadeRepository;

@Component
public class CidadeRepositoryImpl implements CidadeRepository{
	
	@PersistenceContext
	EntityManager manager;
	
	@Override
	@Transactional
	public Cidade salvar(Cidade cidade) {
		
		return manager.merge(cidade);
	}

	@Override
	public Cidade buscar(Long id) {
		
		return manager.find(Cidade.class, id);
	}

	@Override
	public List<Cidade> listar() {
		
		return manager.createQuery("from Cidade", Cidade.class).getResultList();
	}

	@Override
	@Transactional
	public void remover(Long id) {
		Cidade cidade = buscar(id);
		
		if (cidade == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
			manager.remove(cidade);
	}

}
