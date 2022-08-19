package com.example.domain.repository;

import com.example.domain.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface PlaceEntityRepository extends JpaRepository<PlaceEntity, Long> {
    Optional<PlaceEntity> findPlaceEntityByCityAndCountry (String city, String country);
}
