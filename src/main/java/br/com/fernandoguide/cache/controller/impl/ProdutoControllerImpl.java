package br.com.fernandoguide.cache.controller.impl;

import br.com.fernandoguide.cache.controller.ProdutoController;
import br.com.fernandoguide.cache.dto.ProdutoDTO;
import br.com.fernandoguide.cache.entity.Produto;
import br.com.fernandoguide.cache.mapper.ProdutoMapper;
import br.com.fernandoguide.cache.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
public class ProdutoControllerImpl implements ProdutoController {
    private final ProdutoService produtoService;
    private final ProdutoMapper produtoMapper;

    public ProdutoControllerImpl(ProdutoService produtoService, ProdutoMapper produtoMapper) {
        this.produtoService = produtoService;
        this.produtoMapper = produtoMapper;
    }

    @Override
    @PostMapping("/produto")
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO save(@RequestBody ProdutoDTO produtoDTO) {
        Produto produto = produtoMapper.asEntity(produtoDTO);
        return produtoMapper.asDTO(produtoService.save(produto));
    }

    @Override
    @GetMapping("/produto/{id}")
    public ProdutoDTO findById(@PathVariable Integer id) {
        Produto produto = produtoService.findById(id);
        return produtoMapper.asDTO(produto);
    }

    @Override
    @DeleteMapping("produto/{id}")
    public ResponseEntity<ProdutoDTO>  delete(@PathVariable Integer id) {
        produtoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @GetMapping("/produtos")
    public List<ProdutoDTO> list() {
        return produtoMapper.asDTOList(produtoService.findAll());
    }

    @Override
    @GetMapping("produtos/page-query")
    public Page<ProdutoDTO> pageQuery(Pageable pageable) {
        Page<Produto> produtoPage = produtoService.findAll(pageable);
        List<ProdutoDTO> dtoList = produtoPage
                .stream()
                .map(produtoMapper::asDTO).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, produtoPage.getTotalElements());
    }

    @Override
    @PutMapping("/produto/{id}")
    public ResponseEntity<ProdutoDTO> update(@Validated  @RequestBody ProdutoDTO produtoDTO, @PathVariable Integer id) {
        Produto produto = produtoMapper.asEntity(produtoDTO);
        ProdutoDTO responseDTO = produtoMapper.asDTO(produtoService.update(produto, id));
        return ResponseEntity.ok(responseDTO);
    }
}