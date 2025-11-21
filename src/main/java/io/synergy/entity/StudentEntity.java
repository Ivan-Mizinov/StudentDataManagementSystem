package io.synergy.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "students")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String faculty;
    private int grade;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public StudentEntity(String firstName, String lastName, String faculty, int grade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.faculty = faculty;
        this.grade = grade;
    }
}
