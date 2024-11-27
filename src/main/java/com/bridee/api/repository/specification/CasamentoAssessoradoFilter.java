package com.bridee.api.repository.specification;

import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.CasamentoAssessorado;
import com.bridee.api.entity.enums.CasamentoStatusEnum;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class CasamentoAssessoradoFilter {

    public static Specification<CasamentoAssessorado> findByDate(LocalDate date){
        return (root, query, criteriaBuilder) -> {
            Join<CasamentoAssessorado, Casamento> casamentoJoin = root.join("casamento");
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

    public static Specification<CasamentoAssessorado> findByAssessorId(Integer assessorId){
        return (root, query, criteriaBuilder) -> {
            Join<CasamentoAssessorado, Assessor> assessorJoin = root.join("assessor");
            return criteriaBuilder.equal(assessorJoin.get("id"), assessorId);
        };
    }

    public static Specification<CasamentoAssessorado> findByCasamentoStatus(CasamentoStatusEnum casamentoStatusEnum){
        return (root, query, criteriaBuilder) -> {
            Join<CasamentoAssessorado, Casamento> casamentoJoin = root.join("casamento");
            return criteriaBuilder.equal(casamentoJoin.get("status"), CasamentoStatusEnum.PENDENTE_APROVACAO.name());
        };
    }

}
