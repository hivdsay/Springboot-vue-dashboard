package com.example.dashboard_api.service;

import com.example.dashboard_api.DTO.ActivityLogResponse;
import com.example.dashboard_api.entity.ActivityLog;
import com.example.dashboard_api.entity.ActionType;
import com.example.dashboard_api.mapper.ActivityLogMapper;
import com.example.dashboard_api.repository.ActivityLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityLogService {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    // Convert object to JSON string
    public String toJson(Object o) {
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (Exception e) {
            return o != null ? o.toString() : null;
        }
    }

    // Log creation
    public void logCreate(String tableName, String description, Object newEntity) {
        saveLog(getCurrentUsername(), tableName, ActionType.CREATE, description, null, null, toJson(newEntity));
    }

    // Log deletion
    public void logDelete(String tableName, String description, Object oldEntity) {
        saveLog(getCurrentUsername(), tableName, ActionType.DELETE, description, null, toJson(oldEntity), null);
    }

    // Log field update
    public void logFieldUpdate(String tableName, String description, String columnName, String oldValue, String newValue) {
        if ((oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null)) {
            saveLog(getCurrentUsername(), tableName, ActionType.UPDATE, description, columnName, oldValue, newValue);
        }
    }

    // Raw log save method
    public void saveLog(String username, String tableName, ActionType actionType, String description, String columnName,
                        String oldValue, String newValue) {
        ActivityLog log = new ActivityLog();
        log.setUsername(username);
        log.setTableName(tableName);
        log.setActionType(actionType);
        log.setDescription(description);
        log.setColumnName(columnName);
        log.setOldValue(oldValue);
        log.setNewValue(newValue);

        activityLogRepository.save(log);
    }

    // Get all logs
    public List<ActivityLogResponse> getAllLogs() {
        return activityLogRepository.findAllByOrderByIssueDateDesc()
                .stream()
                .map(ActivityLogMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get logs by username
    public List<ActivityLogResponse> getLogsByUsername(String username) {
        return activityLogRepository.findByUsernameOrderByIssueDateDesc(username)
                .stream()
                .map(ActivityLogMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get currently logged-in username
    private String getCurrentUsername() {
        return org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getName();
    }
}
