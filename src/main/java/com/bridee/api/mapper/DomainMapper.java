package com.bridee.api.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface DomainMapper<D, E> {
    D toDomain(E entity);
    List<D> toDomain(List<E> entities);
    default Page<D> toDomain(Page<E> entity){
        Pageable pageRequest = PageRequest.of(entity.getTotalPages(), entity.getSize(), entity.getSort());
        return new PageImpl<>(toDomain(entity.getContent()), pageRequest, entity.getTotalElements());
    }

    default Page<D> toPage(List<D> domain){
        Pageable pageRequest = PageRequest.of(domain.size()/10, 15);
        domain = !domain.isEmpty() ? domain : new ArrayList<>();
        return new PageImpl<>(domain, pageRequest, domain.size());
    }

    default Page<D> toDomainPage(List<E> entity){
        Pageable pageRequest = PageRequest.of(entity.size()/10, 15);
        List<D> content = !entity.isEmpty() ? toDomain(entity) : new ArrayList<>();
        return new PageImpl<>(content, pageRequest, entity.size());
    }
}
