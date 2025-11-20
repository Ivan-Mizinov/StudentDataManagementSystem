package io.synergy.controller;

import io.synergy.dto.StudentStatisticsDto;
import io.synergy.service.StatisticsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("api/statistics/students")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public StudentStatisticsDto getStudentStatistics() {
        return statisticsService.getStatistics();
    }
}
