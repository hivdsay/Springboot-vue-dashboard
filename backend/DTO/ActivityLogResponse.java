// 1. ActivityLogResponse DTO oluştur
package com.example.dashboard_api.DTO;

import com.example.dashboard_api.entity.ActionType;
import java.time.LocalDateTime;

public class ActivityLogResponse {
    private Long id;
    private String username;
    private String tableName;
    private ActionType actionType;
    private String description;
    private LocalDateTime issueDate;
    private String columnName;
    private String oldValue;
    private String newValue;

    // Constructor'lar
    public ActivityLogResponse() {}

    public ActivityLogResponse(Long id, String username, String tableName, ActionType actionType,
                               String description, LocalDateTime issueDate,String columnName,
                               String oldValue, String newValue) {
        this.id = id;
        this.username = username;
        this.tableName = tableName;
        this.actionType = actionType;
        this.description = description;
        this.issueDate = issueDate;
        this.columnName = columnName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    // Getter/Setter'lar
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }

    public ActionType getActionType() { return actionType; }
    public void setActionType(ActionType actionType) { this.actionType = actionType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDateTime issueDate) { this.issueDate = issueDate; }

    public String getColumnName() { return columnName; }
    public void setColumnName(String columnName) { this.columnName = columnName; }

    public String getOldValue() { return oldValue; }
    public void setOldValue(String oldValue) { this.oldValue = oldValue; }

    public String getNewValue() { return newValue; }
    public void setNewValue(String newValue) { this.newValue = newValue; }
}