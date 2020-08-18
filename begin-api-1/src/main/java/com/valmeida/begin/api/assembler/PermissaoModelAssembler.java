package com.valmeida.begin.api.assembler;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.valmeida.begin.api.model.PermissaoModel;
import com.valmeida.begin.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissaoModelAssembler {

    @Autowired
    ModelMapper modelMapper;

    public PermissaoModel toModel(Permissao permissao) {
        return this.modelMapper.map(permissao, PermissaoModel.class);
    }

    public List<PermissaoModel> toCollectionModel(Set<Permissao> permissoes) {
        return permissoes.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
