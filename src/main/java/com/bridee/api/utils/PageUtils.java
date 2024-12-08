package com.bridee.api.utils;

import com.bridee.api.utils.enums.PageEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PageUtils {

    public static <T> Page<T> collectionToPage(Collection<T> collection, Pageable pageable){
        List<T> content = collection.stream().toList();
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), content.size());

        Page page = null;
        if (!content.isEmpty()){
            page = new PageImpl<>(content.subList(start, end), pageable, content.size());
        }else{
            page = new PageImpl<>(content, pageable,0);
        }
        return page;
    }

    public static <T> Page<T> collectionToPage(Collection<T> collection, Page<?> pageImpl){
        List<T> content = collection.stream().toList();
        final int start = 0;
        final int end = content.size();

        Map<String, Object> params = buildPageableMap(pageImpl.getNumber(), pageImpl.getSize(), pageImpl.getSort().toString());
        Page page = null;
        if (!content.isEmpty()){
            page = new PageImpl<>(content.subList(start, end), buildPageable(params), pageImpl.getTotalElements());
        }else{
            page = new PageImpl<>(content, buildPageable(params),0);
        }
        return page;
    }

    private static Map<String, Object> buildPageableMap(Integer pageNumber, Integer size, String sort){
        Map<String, Object> params = new HashMap<>();
        params.put(PageEnum.PAGE_NUMBER.getValue(), String.valueOf(pageNumber));
        params.put(PageEnum.SIZE.getValue(), String.valueOf(size));
        params.put(PageEnum.SORT.getValue(), String.valueOf(sort));
        return params;
    }

    public static Pageable buildPageable(Map<String, Object> params){

        AtomicReference<Integer> pageNumber = new AtomicReference<>(0);
        AtomicReference<Integer> size = new AtomicReference<>(15);
        AtomicReference<String> sort = new AtomicReference<>("ASC");

        params.forEach((key, value) -> {
            if (key.equals(PageEnum.PAGE_NUMBER.getValue())){
                pageNumber.set(Integer.valueOf((String) value));
            } else if (key.equals(PageEnum.SIZE.getValue())) {
                size.set(Integer.valueOf((String) value));
            }else if(key.equals(PageEnum.SORT.getValue())){
                sort.set((String) value);
            }
        });

        return PageRequest.of(pageNumber.get().intValue(), size.get().intValue());

    }


}
