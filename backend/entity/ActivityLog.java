package com.example.dashboard_api.entity;

import jakarta.persistence.*;
import lombok.*;              // <-- Lombok import
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "activity_logs")
// --- Lombok annotations ---
@Getter                    // tüm alanlar için getter
@Setter                    // tüm alanlar için setter
@NoArgsConstructor         // parametresiz constructor
@AllArgsConstructor        // tüm alanları parametre kabul eden constructor
@Builder                   // builder deseni
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "table_name", nullable = false)
    private String tableName;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false)
    private ActionType actionType;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    @Column(name = "issue_date", nullable = false, updatable = false)
    private LocalDateTime issueDate;

    @Column(name = "column_name")
    private String columnName;

    @Column(name = "old_value", columnDefinition = "TEXT")
    private String oldValue;

    @Column(name = "new_value", columnDefinition = "TEXT")
    private String newValue;
}
