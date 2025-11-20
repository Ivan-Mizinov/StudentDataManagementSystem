package io.synergy.service;

import io.synergy.dto.StudentStatisticsDto;
import io.synergy.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private final StudentRepository studentRepository;

    public StatisticsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentStatisticsDto getStatistics() {
        StudentStatisticsDto stats = new StudentStatisticsDto();
        Double avgGrade = studentRepository.findAverageGrade();
        double roundedAvgGrade = avgGrade != null
                ? Math.round(avgGrade * 100.0) / 100.0
                : 0.0;
        stats.setAverageGrade(roundedAvgGrade);

        List<Object[]> facultyData = studentRepository.findFacultyDistribution();
        Map<String, Long> facultyMap = facultyData.stream()
                .collect(Collectors.toMap(
                        arr -> (String) arr[0],
                        arr -> ((Number) arr[1]).longValue()
                ));
        stats.setFacultyDistribution(facultyMap);

        List<Object[]> gradeData = studentRepository.findGradeDistribution();
        Map<Double, Long> gradeMap = new LinkedHashMap<>();
        gradeData.forEach(arr -> {
            double grade = ((Number) arr[0]).doubleValue();
            long count = ((Number) arr[1]).longValue();
            gradeMap.put(grade, count);
        });
        stats.setGradeCounts(gradeMap);

        return stats;
    }
}
