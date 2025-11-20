package io.synergy.repository;

import io.synergy.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    List<StudentEntity> findByFirstName(String firstName);
    List<StudentEntity> findByLastName(String lastName);

    @Query("SELECT AVG(s.grade) FROM StudentEntity s")
    Double findAverageGrade();

    @Query("SELECT s.faculty, COUNT(s) FROM StudentEntity s GROUP BY s.faculty")
    List<Object[]> findFacultyDistribution();

    @Query("SELECT s.grade, COUNT(s) FROM StudentEntity s GROUP BY s.grade ORDER BY s.grade ASC")
    List<Object[]> findGradeDistribution();
}
