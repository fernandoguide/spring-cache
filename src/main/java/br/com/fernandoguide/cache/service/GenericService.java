package br.com.fernandoguide.cache.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenericService<E, M> {
    E save(E entity);

    void deleteById(M id);

    E findById(M id);

    List<E> findAll();

    Page<E> findAll(Pageable pageable);

    E update(E entity, Integer id);
}