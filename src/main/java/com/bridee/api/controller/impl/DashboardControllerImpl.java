package com.bridee.api.controller.impl;

import com.bridee.api.aop.WeddingIdentifier;
import com.bridee.api.dto.response.DashboardResponseDto;
import com.bridee.api.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboards")
@RequiredArgsConstructor
public class DashboardControllerImpl {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponseDto> dashboard(@WeddingIdentifier Integer casamentoId){
        return ResponseEntity.ok(dashboardService.buildDashboard(casamentoId));
    }

}
