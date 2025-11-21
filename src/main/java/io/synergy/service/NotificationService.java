package io.synergy.service;

import io.synergy.dto.NotificationDto;
import io.synergy.entity.NotificationEntity;
import io.synergy.entity.StudentEntity;
import io.synergy.repository.NotificationRepository;
import io.synergy.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final StudentRepository studentRepository;

    public NotificationService(NotificationRepository notificationRepository,
                               StudentRepository studentRepository) {
        this.notificationRepository = notificationRepository;
        this.studentRepository = studentRepository;
    }

    public List<NotificationDto> getStudentNotifications(Long studentId) {
        return notificationRepository.findNotificationsForStudent(studentId);
    }

    public void sendNotification(Long studentId, String title, String message) {
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Студент не найден"));
        NotificationEntity notification = new NotificationEntity(student, title, message);
        notification.setSentAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    public void broadcastFacultyNotification(String faculty, String title, String message) {
        List<StudentEntity> students = studentRepository.findByFaculty(faculty);
        for (StudentEntity student : students) {
            NotificationEntity notification = new NotificationEntity(student, title, message);
            notification.setSentAt(LocalDateTime.now());
            notificationRepository.save(notification);
        }
    }

    public void broadcastAllNotification(String title, String message) {
        List<StudentEntity> students = studentRepository.findAll();
        for (StudentEntity student : students) {
            NotificationEntity notification = new NotificationEntity(student, title, message);
            notification.setSentAt(LocalDateTime.now());
            notificationRepository.save(notification);
        }
    }
}
