package io.synergy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentDto {
    private String firstName;
    private String lastName;
    private String faculty;
    private int grade;
}