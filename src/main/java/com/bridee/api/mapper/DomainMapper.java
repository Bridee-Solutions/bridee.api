package com.bridee.api.mapper;

import org.springframework.data.domain.Page;

import java.util.List;

public interface DomainMapper<D, E> {
    D toDomain(E entity);
    List<D> toDomain(List<E> entities);
    Page<D> toDomain(Page<E> entities);
}
