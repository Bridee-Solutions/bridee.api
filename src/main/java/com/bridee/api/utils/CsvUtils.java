package com.bridee.api.utils;

import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.projection.orcamento.ItemOrcamentoProjection;
import com.bridee.api.projection.orcamento.OrcamentoProjection;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CsvUtils {

    public static byte[] createResumeCostsCsv(OrcamentoProjection orcamentoProjection) throws IOException {

        if (Objects.isNull(orcamentoProjection) ||
                (Objects.isNull(orcamentoProjection.getItemOrcamentos()) &&
                        Objects.isNull(orcamentoProjection.getOrcamentoFornecedores()))){
            throw new ResourceNotFoundException("Orcamento não encontrado para gerar o relátorio csv");
        }

        String nomeArquivo = "%s orcamento %s".formatted(orcamentoProjection.getNomeCasal(),LocalDate.now().toString());
        String tmpDir = System.getProperty("java.io.tmpdir");

        String filename = "%s/%s.csv".formatted(tmpDir, nomeArquivo);
        FileWriter writer = new FileWriter(filename);

        StringBuilder stringBuilder = new StringBuilder();

        createCsvHeader(orcamentoProjection, stringBuilder);
        createCsvBody(orcamentoProjection, stringBuilder);

        writer.write(stringBuilder.toString());

        writer.close();

        InputStream inputStream = new FileInputStream(filename);

        Files.delete(Path.of(filename));

        return inputStream.readAllBytes();
    }

    private static void createCsvHeader(OrcamentoProjection orcamentoProjection, StringBuilder stringBuilder) {

        stringBuilder.append("Orcamento Total;Custo total");

        if (Objects.nonNull(orcamentoProjection.getItemOrcamentos())){
            orcamentoProjection.getItemOrcamentos().forEach(itemOrcamento -> {
                if (Objects.nonNull(itemOrcamento.getCustos())){
                    itemOrcamento.getCustos().forEach(custo -> {
                        stringBuilder.append(";%s".formatted(custo.getNome()));
                    });
                }
            });
        }

        if (Objects.nonNull(orcamentoProjection.getOrcamentoFornecedores())){
            orcamentoProjection.getOrcamentoFornecedores().forEach(orcamentoFornecedor -> {
                stringBuilder.append(";%s".formatted(orcamentoFornecedor.getFornecedor().getSubcategoriaServico().getNome()));
            });
        }

        stringBuilder.append("\n");
    }

    private static void createCsvBody(OrcamentoProjection orcamentoProjection, StringBuilder stringBuilder) {

        String orcamentoTotal = Objects.nonNull(orcamentoProjection.getOrcamentoTotal())
                ? orcamentoProjection.getOrcamentoTotal().toString() : "";

        String orcamentoGasto = Objects.nonNull(orcamentoProjection.getOrcamentoGasto())
                ? orcamentoProjection.getOrcamentoGasto().toString() : "";

        stringBuilder.append("%s;%s".formatted(orcamentoTotal, orcamentoGasto));

        orcamentoProjection.getItemOrcamentos().forEach(item -> {
            item.getCustos().forEach(custo -> {
                stringBuilder.append(";%s".formatted(custo.getPrecoAtual().toString()));
            });
        });

        orcamentoProjection.getOrcamentoFornecedores().forEach(orcamentoFornecedor -> {
            stringBuilder.append(";%s".formatted(orcamentoFornecedor.getPreco().toString()));
        });

    }

}
