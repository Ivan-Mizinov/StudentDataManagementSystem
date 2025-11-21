package io.synergy.service;

import io.synergy.dto.StudentDto;

import io.synergy.dto.StudentResponseDto;
import io.synergy.entity.StudentEntity;
import io.synergy.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED
    )
    public StudentResponseDto save(StudentDto student) {
        StudentEntity entity = mapToEntity(student);
        studentRepository.save(entity);
        return this.mapToResponseDto(entity);
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED
    )
    public StudentResponseDto update(long id, StudentDto student) {
        StudentEntity existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не удалось найти студента с id: " + id));

        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setFaculty(student.getFaculty());
        existingStudent.setGrade(student.getGrade());

        StudentEntity updatedStudent = studentRepository.save(existingStudent);
        return this.mapToResponseDto(updatedStudent);
    }

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED
    )
    public void delete(long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Не удалось найти студента с id: " + id);
        }
        studentRepository.deleteById(id);
    }

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.SUPPORTS,
            readOnly = true
    )
    public List<StudentResponseDto> findByFirstName(String firstName) {
        return studentRepository.findByFirstName(firstName)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.SUPPORTS,
            readOnly = true
    )
    public List<StudentResponseDto> findByLastName(String lastName) {
        return studentRepository.findByLastName(lastName)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.SUPPORTS,
            readOnly = true
    )
    public List<StudentResponseDto> findByFaculty(String faculty) {
        return studentRepository.findByFaculty(faculty)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.SUPPORTS,
            readOnly = true
    )
    public List<StudentResponseDto> findByGrade(int grade) {
        return studentRepository.findByGrade(grade)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.SUPPORTS,
            readOnly = true
    )
    public List<StudentResponseDto> findByFacultyAndGrade(String faculty, int grade) {
        return studentRepository.findByFacultyAndGrade(faculty, grade)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.SUPPORTS,
            readOnly = true
    )
    public List<StudentResponseDto> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public StudentResponseDto mapToResponseDto(StudentEntity entity) {
        return new StudentResponseDto(
                entity.getId(),
                entity.getFirstName() + " " + entity.getLastName(),
                entity.getFaculty(),
                entity.getGrade()
        );
    }

    public StudentEntity mapToEntity(StudentDto dto) {
        return new StudentEntity(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getFaculty(),
                dto.getGrade());
    }
}
