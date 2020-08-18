package com.valmeida.begin.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valmeida.begin.api.model.FormaPagamentoModel;
import com.valmeida.begin.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
	}
	
	public List<FormaPagamentoModel> toCollectionModel(Collection<FormaPagamento> pagamentos) {
			return pagamentos.stream()
					.map(formaPagamento -> toModel(formaPagamento))
					.collect(Collectors.toList());

	}				
	
	
}
