package com.example.core.processor.route;

import com.example.api.operation.RouteProcessorCompare;
import com.example.domain.crud.PlaceCRUD;
import com.example.domain.crud.RouteCRUD;
import com.example.domain.feignFuelInfoAPI.service.FeignClientFuelInfoAPIImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RouteProcessorCompareImplTest {

    private RouteProcessorCompare toTest;

    @Mock
    private FeignClientFuelInfoAPIImpl fuelInfoService;
    @Mock
    private PlaceCRUD placeCRUD;
    @Mock
    private RouteCRUD routeCRUD;

    @BeforeEach
    void setUp() {
        toTest = new RouteProcessorCompareImpl(
                fuelInfoService,
                placeCRUD,
                routeCRUD);
    }


}