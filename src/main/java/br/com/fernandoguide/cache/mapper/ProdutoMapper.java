package br.com.fernandoguide.cache.mapper;

import br.com.fernandoguide.cache.dto.ProdutoDTO;
import br.com.fernandoguide.cache.entity.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProdutoMapper extends GenericMapper<Produto, ProdutoDTO> {
    @Override
    @Mapping(target = "id", ignore = true)
    Produto asEntity(ProdutoDTO dto);
}