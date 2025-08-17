package com.example.dashboard_api.mapper;

import com.example.dashboard_api.DTO.ActivityLogResponse;
import com.example.dashboard_api.entity.ActivityLog;

public class ActivityLogMapper {
    // DTO-Entity dönüşümleri bu sınıfta yapıyoruz.

    // Veritabanındaki ActivityLog nesnesini kullanıcıya göstermek için ActivityLogResponse nesnesine çevirmek.
    public static ActivityLogResponse toDto(ActivityLog entity) {
        ActivityLogResponse dto = new ActivityLogResponse(); // Yeni bir DTO nesnesi oluşturuyoruz.

        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setTableName(entity.getTableName());
        dto.setActionType(entity.getActionType());
        dto.setDescription(entity.getDescription());
        dto.setIssueDate(entity.getIssueDate());
        dto.setColumnName(entity.getColumnName());
        dto.setOldValue(entity.getOldValue());
        dto.setNewValue(entity.getNewValue());

        return dto; // Bu nesne genellikle controller katmanında return ResponseEntity.ok(dto) gibi döndürülür.
    }
}
