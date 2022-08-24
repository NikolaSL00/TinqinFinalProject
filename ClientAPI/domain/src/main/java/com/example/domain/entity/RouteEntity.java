package com.example.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "routes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private PlaceEntity startingPlace;
    @OneToOne
    private PlaceEntity endPlace;

    private Double highwayDistance;
    private Double railwayDistance;
    private Double airDistance;
    private Integer numSearched;
}
