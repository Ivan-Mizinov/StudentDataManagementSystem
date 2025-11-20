package io.synergy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentStatisticsDto {
    private double averageGrade;
    private Map<String, Long> facultyDistribution;
    private Map<Double, Long> gradeCounts;
}
