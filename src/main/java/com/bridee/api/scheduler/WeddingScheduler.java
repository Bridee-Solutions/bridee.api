package com.bridee.api.scheduler;

import com.bridee.api.service.PedidoAssessoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeddingScheduler {

    private final PedidoAssessoriaService pedidoAssessoriaService;

    @Scheduled(cron = "0 0 1 * * *")
    public void invalidateWedding(){
        pedidoAssessoriaService.invalidateWeddings();
    }

}
