package com.valmeida.begin.domain.dto;

import com.valmeida.begin.RestauranteAvro;
import com.valmeida.begin.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteAvroMapper {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteAvro toAvro(final Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteAvro.class);
    }

}
