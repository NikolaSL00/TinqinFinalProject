package com.example.domain.feignClientUsers.service;

import com.example.api.erroring.exception.FeignClientException;
import feignClientUsers.model.UserDTOCreateRequest;
import feignClientUsers.model.UserDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "usersStoreReference",
        url = "${users.store.url}",
        configuration = FeignClientException.class)
public interface UsersServiceFeignClient {

    @GetMapping(value = "/users/search")
    ResponseEntity<UserDTOResponse> findByUsername(@RequestParam(value = "username") String username);

    @PostMapping(value = "/users/", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDTOResponse> createUser(@RequestBody UserDTOCreateRequest userDTOCreateRequest);

}
