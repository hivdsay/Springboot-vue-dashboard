package com.example.dashboard_api.controller;

import com.example.dashboard_api.DTO.ActivityLogResponse;
import com.example.dashboard_api.service.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity-logs")
@CrossOrigin(origins = "*")
public class ActivityLogController {

    @Autowired
    private ActivityLogService activityLogService;

    // Controller'da DTO döndür
    @GetMapping
    public ResponseEntity<List<ActivityLogResponse>> getAllLogs() {
        List<ActivityLogResponse> logs = activityLogService.getAllLogs();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<ActivityLogResponse>> getLogsByUsername(@PathVariable String username) {
        List<ActivityLogResponse> logs = activityLogService.getLogsByUsername(username);
        return ResponseEntity.ok(logs);
    }
}
