package com.example.core.processor.auth;

import com.example.api.base.Error;
import com.example.api.model.auth.LoginRequest;
import com.example.api.model.auth.LoginResult;
import com.example.api.operation.LoginProcessor;
import com.example.domain.feignClientUsers.service.UsersServiceFeignClientImpl;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginProcessorImplTest {

    private LoginProcessor toTest;
    @Mock
    private UsersServiceFeignClientImpl feignClient;

    @BeforeEach
    void setUp() {
        toTest = new LoginProcessorImpl(
                feignClient
        );
    }

    @Test
    void loginWithGoodCredentialsShouldBeSuccessful() {
        // arrange
        LoginRequest testReq = LoginRequest.builder()
                .username("nikola00")
                .password("topsecret")
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

        when(feignClient.findByUsername(anyString()))
                .thenReturn(testUser);

        // act
        Either<Error, LoginResult> processRes = toTest.process(testReq);

        // assert

        assertEquals(testUser.getUsername(),
                processRes.get().getUsername());
    }
    @Test
    void loginWithBadCredentialsShouldReturnError() {
        // arrange
        LoginRequest testReq = LoginRequest.builder()
                .username("nikola")
                .password("topsecret")
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

        when(feignClient.findByUsername(anyString()))
                .thenThrow(new UsernameNotFoundException("Username or password are wrong"));

        // act
        Either<Error, LoginResult> processRes = toTest.process(testReq);

        // assert
        assertEquals("Username or password do not match!",
                processRes.getLeft().getMessage());

        assertEquals(HttpStatus.NOT_FOUND,
                processRes.getLeft().getCode());
    }
}