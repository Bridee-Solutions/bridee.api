package com.bridee.api.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DomainMapper<D, E> {
    D toDomain(E entity);
    List<D> toDomain(List<E> entities);
    default Page<D> toDomain(Page<E> entity){
        Pageable pageRequest = PageRequest.of(entity.getTotalPages(), entity.getSize(), entity.getSort());
        return new PageImpl<>(toDomain(entity.getContent()), pageRequest, entity.getTotalElements());
    }
}
