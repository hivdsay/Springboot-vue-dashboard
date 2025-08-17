package com.example.dashboard_api.repository;

import com.example.dashboard_api.entity.Port;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * Port entity'si için veritabanı işlemlerini yürüten repository arayüzüdür.
 * JpaRepository temel CRUD işlemleri sağlar.
 * JpaSpecificationExecutor filtreleme için kullanılır.
 */
public interface PortRepository extends JpaRepository<Port, String>, JpaSpecificationExecutor<Port> {

    /**
     * En çok limanı olan şehir adını döner.
     */
    @Query("SELECT p.city.name FROM Port p GROUP BY p.city.name ORDER BY COUNT(p.id) DESC")
    List<String> findCityWithMostPorts(Pageable pageable);


    /**
     * 1’den 10’a kadar her bir port sayısı için,
     * o liman sayısına sahip şehir adedini döner.
     *
     * Dönen Object[]’in:
     *   [0] = port_count (Integer)
     *   [1] = city_count (Long)
     */
    @Query(
            value = """
        SELECT 
          t.pcount   AS port_count,
          COUNT(*)   AS city_count
        FROM (
          -- Her şehir için port sayısını hesapla
          SELECT city, COUNT(*) AS pcount
          FROM port
          GROUP BY city
        ) t
        -- Sadece 1–10 arasındaki değerleri al
        WHERE t.pcount BETWEEN 1 AND 10
        -- Aynı port sayısına sahip şehirleri grupla
        GROUP BY t.pcount
        -- 1’den 10’a doğru sırala
        ORDER BY t.pcount
      """,
            nativeQuery = true
    )
    List<Object[]> findCityCountByExactPortCounts1To10();
}


