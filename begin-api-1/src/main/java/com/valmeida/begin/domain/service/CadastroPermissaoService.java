package com.valmeida.begin.domain.service;

import com.valmeida.begin.domain.exception.GrupoNaoEncontradoException;
import com.valmeida.begin.domain.exception.PermissaoNaoEncontradaException;
import com.valmeida.begin.domain.model.Permissao;
import com.valmeida.begin.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroPermissaoService {

    @Autowired
    PermissaoRepository permissaoRepository;

    public Permissao buscarOuFalhar(Long permissaoId) {
        return this.permissaoRepository.findById(permissaoId)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }
}
