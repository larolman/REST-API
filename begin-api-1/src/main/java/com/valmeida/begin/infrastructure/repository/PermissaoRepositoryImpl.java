package com.valmeida.begin.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.valmeida.begin.domain.model.Permissao;
import com.valmeida.begin.domain.repository.PermissaoRepository;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository{
	
	@PersistenceContext
	EntityManager manager;
	
	@Override
	public List<Permissao> listar() {
		
		return manager.createQuery("from Permissao", Permissao.class).getResultList();
	}

	@Override
	public Permissao buscar(Long id) {
		
		return manager.find(Permissao.class, id);
	}

	@Override
	public Permissao salvar(Permissao permissao) {
		
		return manager.merge(permissao);
	}

	@Override
	public void remover(Permissao permissao) {
		
		permissao = buscar(permissao.getId());
		manager.remove(permissao);
	}
	
	
}
