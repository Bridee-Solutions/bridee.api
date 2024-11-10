package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.TarefaResponseDto;
import com.bridee.api.dto.response.TarefasResponseDto;
import com.bridee.api.entity.Tarefa;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TarefasResponseMapper{

    default List<TarefasResponseDto> toTarefasDto(List<Tarefa> tarefas){

        List<TarefasResponseDto> tarefasResponseDtos = new ArrayList<>();
        Map<Integer, List<Tarefa>> tasks = tarefas.stream()
                .collect(Collectors.groupingBy((tarefa) -> tarefa.getDataLimite().getYear()));

        tasks.forEach((key, value) -> {
            tarefasResponseDtos.add(TarefasResponseDto.builder()
                    .ano(key)
                    .tarefas(buildYearlyTasksResponse(value))
                    .build());
        });
        return tarefasResponseDtos;

    }

    default TarefasResponseDto.YearlyTasks buildYearlyTasksResponse(List<Tarefa> tarefas){
        return TarefasResponseDto.YearlyTasks.builder()
                .janeiro(tarefasJaneiro(tarefas))
                .fevereiro(tarefasFevereiro(tarefas))
                .marco(tarefasMarco(tarefas))
                .abril(tarefasAbril(tarefas))
                .maio(tarefasMaio(tarefas))
                .junho(tarefasJunho(tarefas))
                .julho(tarefasJulho(tarefas))
                .agosto(tarefasAgosto(tarefas))
                .setembro(tarefasSetembro(tarefas))
                .outubro(tarefasOutubro(tarefas))
                .novembro(tarefasNovembro(tarefas))
                .dezembro(tarefasDezembro(tarefas))
                .atrasadas(tarefasAtrasadas(tarefas))
                .build();
    }

    List<TarefaResponseDto> toResponseDto(List<Tarefa> tarefas);

    default List<TarefaResponseDto> tarefasJaneiro(List<Tarefa> tarefas){
        List<Tarefa> januaryTasks = tarefas.stream().filter(tarefa -> tarefa.getDataLimite().getMonthValue() == 1).toList();
        return toResponseDto(januaryTasks);
    }

    default List<TarefaResponseDto> tarefasFevereiro(List<Tarefa> tarefas){
        List<Tarefa> februaryTasks = tarefas.stream().filter(tarefa -> tarefa.getDataLimite().getMonthValue() == 2).toList();
        return toResponseDto(februaryTasks);
    }

    default List<TarefaResponseDto> tarefasMarco(List<Tarefa> tarefas){
        List<Tarefa> marchTasks = tarefas.stream().filter(tarefa -> tarefa.getDataLimite().getMonthValue() == 3).toList();
        return toResponseDto(marchTasks);
    }

    default List<TarefaResponseDto> tarefasAbril(List<Tarefa> tarefas){
        List<Tarefa> aprilTasks = tarefas.stream().filter(tarefa -> tarefa.getDataLimite().getMonthValue() == 4).toList();
        return toResponseDto(aprilTasks);
    }

    default List<TarefaResponseDto> tarefasMaio(List<Tarefa> tarefas){
        List<Tarefa> mayTasks = tarefas.stream().filter(tarefa -> tarefa.getDataLimite().getMonthValue() == 5).toList();
        return toResponseDto(mayTasks);
    }

    default List<TarefaResponseDto> tarefasJunho(List<Tarefa> tarefas){
        List<Tarefa> juneTasks = tarefas.stream().filter(tarefa -> tarefa.getDataLimite().getMonthValue() == 6).toList();
        return toResponseDto(juneTasks);
    }

    default List<TarefaResponseDto> tarefasJulho(List<Tarefa> tarefas){
        List<Tarefa> julyTasks = tarefas.stream().filter(tarefa -> tarefa.getDataLimite().getMonthValue() == 7).toList();
        return toResponseDto(julyTasks);
    }

    default List<TarefaResponseDto> tarefasAgosto(List<Tarefa> tarefas){
        List<Tarefa> augustTasks = tarefas.stream().filter(tarefa -> tarefa.getDataLimite().getMonthValue() == 8).toList();
        return toResponseDto(augustTasks);
    }

    default List<TarefaResponseDto> tarefasSetembro(List<Tarefa> tarefas){
        List<Tarefa> septemberTasks = tarefas.stream().filter(tarefa -> tarefa.getDataLimite().getMonthValue() == 9).toList();
        return toResponseDto(septemberTasks);
    }

    default List<TarefaResponseDto> tarefasOutubro(List<Tarefa> tarefas){
        List<Tarefa> octoberTasks = tarefas.stream().filter(tarefa -> tarefa.getDataLimite().getMonthValue() == 10).toList();
        return toResponseDto(octoberTasks);
    }

    default List<TarefaResponseDto> tarefasNovembro(List<Tarefa> tarefas){
        List<Tarefa> novemberTasks = tarefas.stream().filter(tarefa -> tarefa.getDataLimite().getMonthValue() == 11).toList();
        return toResponseDto(novemberTasks);
    }

    default List<TarefaResponseDto> tarefasDezembro(List<Tarefa> tarefas){
        List<Tarefa> decemberTasks = tarefas.stream().filter(tarefa -> tarefa.getDataLimite().getMonthValue() == 12).toList();
        return toResponseDto(decemberTasks);
    }

    default List<TarefaResponseDto> tarefasAtrasadas(List<Tarefa> tarefas){
        List<Tarefa> lateTasks = tarefas.stream().filter(tarefa -> tarefa.getDataLimite().isBefore(LocalDate.now())).toList();
        return toResponseDto(lateTasks);
    }
}
