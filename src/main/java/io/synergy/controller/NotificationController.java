package io.synergy.controller;

import io.synergy.dto.NotificationDto;
import io.synergy.entity.StudentEntity;
import io.synergy.entity.UserEntity;
import io.synergy.service.NotificationService;
import io.synergy.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;

    public NotificationController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @PostMapping("/api/notifications/send")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> sendNotification(
            @RequestParam Long studentId,
            @RequestParam String title,
            @RequestParam String message
    ) {
        notificationService.sendNotification(studentId, title, message);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/api/notifications/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<NotificationDto> getStudentNotifications(@PathVariable Long studentId, Authentication authentication) {
        UserEntity user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new AccessDeniedException("Пользователь не найден"));
        if (!user.getRole().equals("ADMIN")) {
            StudentEntity student = user.getStudent();
            if (student == null) {
                throw new AccessDeniedException("У пользователя нет связанного профиля студента");
            }

            if (!student.getId().equals(studentId)) {
                throw new AccessDeniedException("Доступ запрещен: studentId не соответствует профилю пользователя");
            }
        }
        return notificationService.getStudentNotifications(studentId);
    }

    @PostMapping("/api/notifications/broadcastFaculty")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> broadcastFacultyNotification(
            @RequestParam String faculty,
            @RequestParam String title,
            @RequestParam String message
    ) {
        notificationService.broadcastFacultyNotification(faculty, title, message);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/api/notifications/broadcastAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> broadcastNotification(
            @RequestParam String title,
            @RequestParam String message
    ) {
        notificationService.broadcastAllNotification(title, message);
        return ResponseEntity.accepted().build();
    }
}
