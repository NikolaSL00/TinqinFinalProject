package com.example.core.processor.auth;

import com.example.api.base.Error;
import com.example.api.erroring.exception.UserCreationFailureException;
import com.example.api.model.auth.LoginRequest;
import com.example.api.model.auth.RegisterRequest;
import com.example.api.model.auth.RegisterResult;
import com.example.api.operation.RegisterProcessor;
import com.example.domain.feignClientUsers.service.UsersServiceFeignClientImpl;
import feignClientUsers.model.UserDTOResponse;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterProcessorImplTest {

    private RegisterProcessor toTest;

    @Mock
    private UsersServiceFeignClientImpl feignClient;

    @BeforeEach
    void setUp() {
        toTest = new RegisterProcessorImpl(
                feignClient
        );
    }

    @Test
    void registerWithGoodCredentialsShouldBeSuccessful() {
        // arrange
        RegisterRequest testReq = RegisterRequest.builder()
                .username("nikola00")
                .password("topsecret")
                .age(22)
                .firstName("Nikola")
                .lastName("Slavchev")
                .build();

        feignClientUsers.model.ClientUserDetails testUser = feignClientUsers.model.ClientUserDetails
                .builder()
                .age(22)
                .firstName("Nikola")
                .lastName("Slavchev")
                .password("topsecret")
                .authorities(Collections.emptyList())
                .username("nikola00")
                .build();

        UserDTOResponse testResDTO = UserDTOResponse.builder()
                .age(22)
                .username("nikola00")
                .firstName("Nikola")
                .lastName("Slavchev")
                .password("topsecret")
                .build();


        when(feignClient.findByUsername(anyString()))
                .thenReturn(testUser);

        when(feignClient.createUser(any()))
                .thenReturn(testResDTO);

        // act
        Either<Error, RegisterResult> processRes = toTest.process(testReq);

        // assert
        assertEquals(testResDTO.getUsername(),
                processRes.get().getUsername());
    }

    @Test
    void registerWithBadCredentialsReturnError() {
        // arrange
        RegisterRequest testReq = RegisterRequest.builder()
                .username("nikola00")
                .password("topsecret")
                .age(22)
                .firstName("Nikola")
                .lastName("Slavchev")
                .build();

        feignClientUsers.model.ClientUserDetails testUser = feignClientUsers.model.ClientUserDetails
                .builder()
                .age(22)
                .firstName("Nikola")
                .lastName("Slavchev")
                .password("topsecret")
                .authorities(Collections.emptyList())
                .username("nikola00")
                .build();

        when(feignClient.createUser(any()))
                .thenThrow(new UserCreationFailureException("User with that username is already present."));

        // act
        Either<Error, RegisterResult> processRes = toTest.process(testReq);

        // assert
        assertEquals(
                "User with that username is already present!",
                processRes.getLeft().getMessage());

        assertEquals(HttpStatus.CONFLICT,
            processRes.getLeft().getCode());
    }
}