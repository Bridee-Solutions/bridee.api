package com.bridee.api.repository.specification;

import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.PedidoAssessoria;
import com.bridee.api.entity.enums.PedidoAssessoriaStatusEnum;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class PedidoAssessoriaFilter {

    public static Specification<PedidoAssessoria> findByDate(LocalDate date){
        return (root, query, criteriaBuilder) -> {
            Join<PedidoAssessoria, Casamento> casamentoJoin = root.join("casamento");
            DataFilterDto dateFilter = buildFilterDate(date);
            return criteriaBuilder.between(casamentoJoin.get("dataFim"),
                    dateFilter.getDataInicio(), dateFilter.getDataFim());
        };
    }

    private static DataFilterDto buildFilterDate(LocalDate date) {
        int ano = date.getYear();
        int mes = date.getMonthValue();
        LocalDate initialFilterDate = LocalDate.of(ano, mes, date.with(TemporalAdjusters.firstDayOfMonth()).getDayOfMonth());
        LocalDate finalFilterDate = LocalDate.of(ano, mes, date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        return new DataFilterDto(initialFilterDate, finalFilterDate);
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
