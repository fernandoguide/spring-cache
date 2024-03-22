package br.com.fernandoguide.cache.service.impl;

import br.com.fernandoguide.cache.dao.ProdutoRepository;
import br.com.fernandoguide.cache.entity.Produto;
import br.com.fernandoguide.cache.service.ProdutoService;
import br.com.fernandoguide.cache.service.exceptions.ObjectNotFoundException;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

import static java.util.Objects.requireNonNullElse;

@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService {
    private final ProdutoRepository repository;

    public ProdutoServiceImpl(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    @CachePut(value = "produto")
    public Produto save(Produto entity) {
        return repository.save(entity);
    }



    @Override
    @CachePut(value = "produto")
    public void deleteById(Integer id) {
        Produto p = findById(id);
        repository.deleteById(p.getId());
    }

    @Override
    @CachePut(value = "produto")
    public Produto findById(Integer id) {
        return  repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
               "Objeto não encontrado com o  id: " + id  + " do tipo " + Produto.class.getSimpleName()));

    }

    @Override
    @CachePut(value = "produto")
    public List<Produto> findAll() {
        return  repository.findAll();
    }

    @Override
    @CachePut(value = "produto")
    public Page<Produto> findAll(Pageable pageable) {
        Page<Produto> entityPage = repository.findAll(pageable);
        List<Produto> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    @CachePut(value = "produto")
    public Produto update(Produto entity, Integer id) {
       Produto newObj = findById(id);
       newObj.setPreco(requireNonNullElse(entity.getPreco(),newObj.getPreco()));
       newObj.setNome(requireNonNullElse(entity.getNome(),newObj.getNome()));
       newObj.setDescricao(requireNonNullElse(entity.getDescricao(),newObj.getDescricao()));
       newObj.setId(id);
       return save(newObj);
    }
}