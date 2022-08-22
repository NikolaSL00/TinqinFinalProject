package com.example.domain.crud;

import com.example.api.model.place.Coordinate;
import com.example.api.model.place.PlaceCreateRequest;
import com.example.api.model.place.PlaceFindRequest;
import com.example.domain.entity.PlaceEntity;
import com.example.domain.feignFindPlaceAPI.service.RestApiFindPlaceService;
import com.example.domain.feignLocatePlaceAPI.model.Place;
import com.example.domain.repository.PlaceEntityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaceCRUD {

    private final PlaceEntityRepository placeRepository;
    private final RestApiFindPlaceService restApiFindPlaceService;

    public PlaceCRUD(PlaceEntityRepository placeRepository, RestApiFindPlaceService restApiFindPlaceService) {
        this.placeRepository = placeRepository;
        this.restApiFindPlaceService = restApiFindPlaceService;
    }

    public PlaceEntity createPlace(PlaceCreateRequest placeCreateRequest){
        Place place = restApiFindPlaceService.findPlace(PlaceFindRequest
                .builder()
                .city(placeCreateRequest.getCity())
                .country(placeCreateRequest.getCountry())
                .build());

        PlaceEntity entityPlace = PlaceEntity
                .builder()
                .city(place.getCity())
                .country(place.getCountry())
                .name(place.getName())
                .longitude(place.getLongitude())
                .latitude(place.getLatitude())
                .numDestination(placeCreateRequest.getNumDestination())
                .numDeparture(placeCreateRequest.getNumDeparture())
                .build();

        placeRepository.save(entityPlace);


        return entityPlace;
    }

    public Optional<PlaceEntity> getPlace(Coordinate coordinate) {
        return placeRepository.findPlaceEntityByCityAndCountry(coordinate.getCity(),
                coordinate.getCountry());
    }

    public Optional<PlaceEntity> getPlaceByCity(String city){
        return placeRepository.findPlaceEntityByCity(city);
    }

    public PlaceEntity updatePlace (PlaceEntity place){
        return placeRepository.save(place);
    }
}
