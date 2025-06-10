package com.bridee.api.repository.specification;

import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.PedidoAssessoria;
import com.bridee.api.entity.enums.PedidoAssessoriaStatusEnum;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PedidoAssessoriaFilter {

    public static Specification<PedidoAssessoria> findByDate(Integer ano){
        return (root, query, criteriaBuilder) -> {
            Join<PedidoAssessoria, Casamento> casamentoJoin = root.join("casamento");
            DateFilterDto dateFilter = buildFilterDate(ano);
            return criteriaBuilder.between(casamentoJoin.get("dataFim"),
                    dateFilter.getDataInicio(), dateFilter.getDataFim());
        };
    }

    private static DateFilterDto buildFilterDate(Integer ano) {
        LocalDate initialFilterDate = LocalDate.of(ano, 1, 1);
        LocalDate finalFilterDate = LocalDate.of(ano, 12, 31);
        return new DateFilterDto(initialFilterDate, finalFilterDate);
    }

    public static Specification<PedidoAssessoria> findByAssessorId(Integer assessorId){
        return (root, query, criteriaBuilder) -> {
            Join<PedidoAssessoria, Assessor> assessorJoin = root.join("assessor");
            return criteriaBuilder.equal(assessorJoin.get("id"), assessorId);
        };
    }

    public static Specification<PedidoAssessoria> findByPedidoAssessoradoStatus(PedidoAssessoriaStatusEnum pedidoAssessoriaStatusEnum){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), pedidoAssessoriaStatusEnum);
    }

}
