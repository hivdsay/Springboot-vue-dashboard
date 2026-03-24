package com.example.dashboard_api.repository;

import com.example.dashboard_api.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

    //  Tüm log kayıtlarını, issueDate’e (oluşturulma zamanı) göre en yeniden en eskiye doğru getirir.
    List<ActivityLog> findAllByOrderByIssueDateDesc();

    //  Belirli bir kullanıcıya (username) ait log’ları, yine oluşturulma zamanına göre en yeni ilk
    List<ActivityLog> findByUsernameOrderByIssueDateDesc(String username);
}

