package com.bridee.api.repository;

import com.bridee.api.entity.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {

    Optional<Fornecedor> findByCnpj(String cnpj);
}
