package com.valmeida.begin.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Grupo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column
	private String nome;
	
	@ManyToMany
	@JoinTable(name = "grupo_permissao",
				joinColumns =  @JoinColumn(name = "grupo_id"),
				inverseJoinColumns = @JoinColumn(name = "permissao_id"))
	Set<Permissao> permissoes = new HashSet<>();

	public void adicionarPermissao(Permissao permissao) {
		this.getPermissoes().add(permissao);
	}

	public void removerPermissao(Permissao permissao) {
		this.getPermissoes().remove(permissao);
	}
}
