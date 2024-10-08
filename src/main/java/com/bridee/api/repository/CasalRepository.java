package com.bridee.api.repository;

import com.bridee.api.entity.Casal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CasalRepository extends JpaRepository<Casal, Integer> {

    boolean existsByEmail(String email);
}
