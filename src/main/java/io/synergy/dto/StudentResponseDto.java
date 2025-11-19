package io.synergy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentResponseDto {
    private Long id;
    private String fullName;
    private String faculty;
    private int grade;
}
