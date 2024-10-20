package com.bridee.api.repository.specification;


import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Convite;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ConvidadoFilter {

    public static Specification<Convidado> hasNome(String nome){
        return ((root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.upper(root.get("nome")), "%"+nome.toUpperCase()+"%"));
    }

    public static Specification<Convidado> hasCasamentoId(Integer casamentoId){
        return (root, query, criteriaBuilder) -> {
            Join<Convidado, Convite> convite = root.join("convite");
            Join<Convite, Casamento> casamento = convite.join("casamento");

            return criteriaBuilder.equal(casamento.get("id"), casamentoId);
        };
    }

}
