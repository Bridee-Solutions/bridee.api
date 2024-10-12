package com.bridee.api.repository;

import com.bridee.api.entity.Fornecedor;
import com.bridee.api.projection.FornecedorResponseProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {

    Optional<Fornecedor> findByCnpj(String cnpj);

    @Query("""
            SELECT f.nome as nome,
            f.informacaoAssociado.visaoGeral as visaoGeral,
            f.informacaoAssociado.local as local,
            (SELECT AVG(a.nota) FROM Avaliacao a WHERE a.fornecedor.id = :id GROUP BY a.nota) as media,
            (SELECT COUNT(a) FROM Avaliacao a WHERE a.fornecedor.id = :id GROUP BY a) as totalAvaliacoes
            FROM Fornecedor f WHERE f.id = :id
            """)
    FornecedorResponseProjection findFornecedorDetails(Integer id);
}
