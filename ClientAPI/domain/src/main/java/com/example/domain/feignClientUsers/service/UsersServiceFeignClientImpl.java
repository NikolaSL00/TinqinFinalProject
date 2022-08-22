package com.example.domain.feignClientUsers.service;

import com.example.api.erroring.exception.UserCreationFailureException;
import com.example.api.erroring.exception.UserNotFoundException;
import com.example.api.model.auth.RegisterRequest;
import feignClientUsers.model.ClientUserDetails;
import feignClientUsers.model.UserDTOCreateRequest;
import feignClientUsers.model.UserDTOResponse;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UsersServiceFeignClientImpl{
    private final UsersServiceFeignClient usersServiceFeignClient;

    public UsersServiceFeignClientImpl(UsersServiceFeignClient usersServiceFeignClient) {
        this.usersServiceFeignClient = usersServiceFeignClient;
    }

    public UserDetails findByUsername(String username) {
        try {
            ResponseEntity<UserDTOResponse> result = usersServiceFeignClient.findByUsername(username);

            UserDTOResponse user = result.getBody();

            return ClientUserDetails.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .age(user.getAge())
                    .authorities(new ArrayList<>())
                    .build();

        } catch (FeignException.NotFound ex) {
            throw new UserNotFoundException("User not found with username: " + username);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public UserDTOResponse createUser(RegisterRequest registerRequest) {

        try {
            ResponseEntity<UserDTOResponse> result = usersServiceFeignClient.createUser(
                    UserDTOCreateRequest
                            .builder()
                            .username(registerRequest.getUsername())
                            .password(registerRequest.getPassword())
                            .firstName(registerRequest.getFirstName())
                            .lastName(registerRequest.getLastName())
                            .age(registerRequest.getAge())
                            .build()
            );

            return result.getBody();

        } catch (Exception ex) {
            if (ex instanceof FeignException.Conflict) {
                throw new UserCreationFailureException("User with that username is already present.");
            }
            throw new RuntimeException();
        }

    }

}
