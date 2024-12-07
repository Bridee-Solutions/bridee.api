package com.bridee.api.repository;

import com.bridee.api.entity.TipoCasamento;
import com.bridee.api.entity.TipoCerimonia;

import feign.Param;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipoCerimoniaRepository extends JpaRepository<TipoCerimonia, Integer> {
        List<TipoCerimonia> findCerimoniasById(Integer id);
    }