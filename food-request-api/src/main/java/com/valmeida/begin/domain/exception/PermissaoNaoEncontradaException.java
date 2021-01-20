package com.valmeida.begin.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public PermissaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public PermissaoNaoEncontradaException(Long permissaoId) {
        this(String.format("Permissão com id %d não encontrada", permissaoId));

    }
}
