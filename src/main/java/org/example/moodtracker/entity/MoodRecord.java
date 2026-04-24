package org.example.moodtracker.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author duoyian
 * @date 2026/4/24
 */
@Entity
@Table(name = "mood_records")
@Data
public class MoodRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId = 1L; // 简化：默认使用用户ID 1

    @Column(name = "mood_level", nullable = false)
    private Integer moodLevel; // 0-5

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "tags", length = 200)
    private String tags; // 逗号分隔的标签

    @Column(name = "weather", length = 20)
    private String weather;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (recordDate == null) {
            recordDate = LocalDate.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
