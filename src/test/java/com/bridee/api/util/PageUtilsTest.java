package com.bridee.api.util;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PageUtilsTest<T> {

    public static <T> PageImpl<T> buildPageImpl(List<T> elements){
        return new PageImpl<>(elements);
    }

    public static <T> PageImpl<T> buildPageImpl(T elements){
        return new PageImpl<>(List.of(elements));
    }

    public static Pageable buildPageable(Integer pageNumber, Integer size, Sort.Direction direction){
        return PageRequest.of(pageNumber, size, direction);
    }

    public static Pageable buildPageable(Integer pageNumber, Integer size){
        return PageRequest.of(pageNumber, size);
    }

}
