package io.synergy.controller;

import io.synergy.dto.StudentDto;
import io.synergy.dto.StudentResponseDto;
import io.synergy.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/api/service/Student")
    public StudentResponseDto addStudent(@RequestBody StudentDto student) {
        return studentService.save(student);
    }

    @PutMapping("/api/service/Student/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public StudentResponseDto updateStudent(
            @PathVariable Long id,
            @RequestBody StudentDto student) {
        return studentService.update(id, student);
    }

    @DeleteMapping("/api/service/Student/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
    }

    @GetMapping("/api/service/Student/searchByFirstName")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<StudentResponseDto> findByFirstName(
            @RequestParam String firstName) {
        return studentService.findByFirstName(firstName);
    }

    @GetMapping("/api/service/Student/searchByLastName")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<StudentResponseDto> findByLastName(
            @RequestParam String lastName) {
        return studentService.findByLastName(lastName);
    }

    @GetMapping("/api/service/Student/searchByFaculty")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<StudentResponseDto> findByFaculty(
            @RequestParam String faculty
    ) {
        return studentService.findByFaculty(faculty);
    }

    @GetMapping("/api/service/Student/searchByGrade")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<StudentResponseDto> findByGrade(
            @RequestParam int grade
    ) {
        return studentService.findByGrade(grade);
    }

    @GetMapping("/api/service/Student/searchByFacultyAndGrade")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<StudentResponseDto> findByFacultyAndGrade(
            @RequestParam String faculty,
            @RequestParam int grade
    ) {
        return studentService.findByFacultyAndGrade(faculty, grade);
    }

    @GetMapping("/api/service/Students")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<StudentResponseDto> getAllStudents() {
        return studentService.findAll();
    }
}
