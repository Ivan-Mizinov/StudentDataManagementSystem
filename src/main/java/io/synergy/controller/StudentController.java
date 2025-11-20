package io.synergy.controller;

import io.synergy.dto.StudentDto;
import io.synergy.dto.StudentResponseDto;
import io.synergy.service.StudentService;
import org.springframework.http.HttpStatus;
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
    public StudentResponseDto updateStudent(
            @PathVariable Long id,
            @RequestBody StudentDto student) {
        return studentService.update(id, student);
    }

    @DeleteMapping("/api/service/Student/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
    }

    @GetMapping("/api/service/Student/searchByFirstName")
    public List<StudentResponseDto> findByFirstName(
            @RequestParam String firstName) {
        return studentService.findByFirstName(firstName);
    }

    @GetMapping("/api/service/Student/searchByLastName")
    public List<StudentResponseDto> findByLastName(
            @RequestParam String lastName) {
        return studentService.findByLastName(lastName);
    }

    @GetMapping("/api/service/Students")
    public List<StudentResponseDto> getAllStudents() {
        return studentService.findAll();
    }
}
