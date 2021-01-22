package com.valmeida.begin.api.controller;

import com.valmeida.begin.api.assembler.UsuarioModelAssembler;
import com.valmeida.begin.api.model.UsuarioModel;
import com.valmeida.begin.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

    @Autowired
    private CadastroRestauranteService restauranteService;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @GetMapping
    public List<UsuarioModel> listarResponsaveis(final @PathVariable("restauranteId") Long restauranteId) {
        final var restaurante = this.restauranteService.buscarOuFalhar(restauranteId);

        return usuarioModelAssembler.toCollectionModel(restaurante.getUsuariosResponsaveis());
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void associarResponsavel(final @PathVariable("restauranteId") Long restauranteId,
                                     final @PathVariable("usuarioId") Long usuarioId) {
        this.restauranteService.associarUsuarioResponsavel(restauranteId, usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void desassociarResponsavel(final @PathVariable("restauranteId") Long restauranteId,
                                     final @PathVariable("usuarioId") Long usuarioId) {
        this.restauranteService.desassociarUsuarioResponsavel(restauranteId, usuarioId);
    }

}
