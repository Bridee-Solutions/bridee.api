package com.bridee.api.repository;

import com.bridee.api.entity.Assessor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssessorRepository extends JpaRepository<Assessor, Integer> {

    boolean existsByCnpjOrEmail(String cnpj, String email);
}
