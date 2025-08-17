package com.example.dashboard_api.repository;

import com.example.dashboard_api.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, String>, JpaSpecificationExecutor<City> {
    //Bu, dinamik filtreleme (Specification) için gerekli olan metotları sağlar. (findAll(Specification) gibi)
}


