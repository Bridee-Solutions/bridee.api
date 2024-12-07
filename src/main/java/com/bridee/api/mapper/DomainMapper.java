package com.bridee.api.mapper;

import com.bridee.api.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DomainMapper<D, E> {
    D toDomain(E entity);
    List<D> toDomain(List<E> entities);

    default Page<D> toDomain(Page<E> entity){
        return new PageImpl<>(toDomain(entity.getContent()), entity.getPageable(), entity.getTotalElements());
    }

    default Page<D> toDomainPage(List<E> entity, Pageable pageable){
        return PageUtils.collectionToPage(toDomain(entity), pageable);
    }
}
