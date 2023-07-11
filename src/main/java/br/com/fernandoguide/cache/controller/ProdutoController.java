package br.com.fernandoguide.cache.controller;

import br.com.fernandoguide.cache.dto.ProdutoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api(tags = "Produto API")
public interface ProdutoController {
    @ApiOperation("Add new data")
    public ProdutoDTO save(@RequestBody ProdutoDTO produto);

    @ApiOperation("Find by Id")
    public ProdutoDTO findById(@PathVariable Integer id);

    @ApiOperation("Delete based on primary key")
    public ResponseEntity<ProdutoDTO> delete(@PathVariable Integer id);

    @ApiOperation("Find all data")
    public List<ProdutoDTO> list();

    @ApiOperation("Pagination request")
    public Page<ProdutoDTO> pageQuery(Pageable pageable);

    @ApiOperation("Update one data")
    public ResponseEntity<ProdutoDTO> update(@RequestBody ProdutoDTO dto, @PathVariable Integer id);
}