package com.cg.repository;

import com.cg.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
    Boolean existsByName(String name);
}
