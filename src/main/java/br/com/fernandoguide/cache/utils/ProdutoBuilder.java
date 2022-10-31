package br.com.fernandoguide.cache.utils;

import br.com.fernandoguide.cache.dto.ProdutoDTO;
import br.com.fernandoguide.cache.entity.Produto;

import java.util.Arrays;
import java.util.List;

public class ProdutoBuilder {
    public static List<ProdutoDTO> getListDTO() {
        return Arrays.asList(new ProdutoDTO(1,"Teste QA","QA",3000.0),
                new ProdutoDTO(2,"Teste Dev","DEV",8000.0));
    }

    public static List<Produto> getListEntities() {
        return Arrays.asList(new Produto(1,"Teste QA","QA",3000.0),
                new Produto(2,"Teste Dev","DEV",8000.0));
    }

    public static Produto getEntity() {
        return  new Produto(1,"Teste Dev","DEV",8000.0);
    }

    public static ProdutoDTO getDTO() {
        return  new ProdutoDTO(1,"Teste Dev","DEV",8000.0);
    }
}

