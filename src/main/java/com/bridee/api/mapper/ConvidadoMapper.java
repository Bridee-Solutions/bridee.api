package com.bridee.api.mapper;

import com.bridee.api.model.Convidado;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConvidadoMapper implements DomainMapper<Convidado, Convidado>, EntityMapper<Convidado, Convidado> {

    @Override
    public Convidado toDomain(Convidado entity) {
        return entity;
    }

    @Override
    public List<Convidado> toDomain(List<Convidado> entities) {
        return entities;
    }

    @Override
    public Convidado toEntity(Convidado domain) {
        return domain;
    }

    @Override
    public List<Convidado> toEntity(List<Convidado> domains) {
        return domains;
    }
}

