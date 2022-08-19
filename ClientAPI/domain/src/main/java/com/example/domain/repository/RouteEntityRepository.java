package com.example.domain.repository;

import com.example.domain.entity.PlaceEntity;
import com.example.domain.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteEntityRepository extends JpaRepository<RouteEntity, Long> {
    Optional<RouteEntity> findRouteEntityByStartingPlaceAndEndPlace(PlaceEntity startingPlace,
                                                                    PlaceEntity endPlace);
}
