package io.synergy.repository;

import io.synergy.dto.NotificationDto;
import io.synergy.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    @Query("SELECT NEW io.synergy.dto.NotificationDto(n.title, n.message, n.sentAt) " +
            "FROM NotificationEntity n " +
            "WHERE n.student.id = :studentId " +
            "ORDER BY n.sentAt DESC")
    List<NotificationDto> findNotificationsForStudent(@Param("studentId") Long studentId);

}
