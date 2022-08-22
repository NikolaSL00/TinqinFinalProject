package com.example.domain.crud;

import com.example.domain.entity.PlaceEntity;
import com.example.domain.entity.RouteEntity;
import com.example.domain.repository.RouteEntityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RouteCRUD {

    private final RouteEntityRepository routeRepository;

    public RouteCRUD(RouteEntityRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public Optional<RouteEntity> getRouteByStartingAndEndingPlace(PlaceEntity startingPlace, PlaceEntity endPlace){
        return routeRepository.findRouteEntityByStartingPlaceAndEndPlace(startingPlace, endPlace);
    }

    public void createRoute(RouteEntity route) {
        routeRepository.save(route);
    }

    public void updateRoute(RouteEntity route) {
        routeRepository.save(route);
    }
}
