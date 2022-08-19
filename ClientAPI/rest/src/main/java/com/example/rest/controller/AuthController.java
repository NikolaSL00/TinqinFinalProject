package com.example.rest.controller;


import com.example.api.base.Error;
import com.example.api.model.auth.*;
import com.example.api.operation.LoginProcessor;
import com.example.api.operation.RegisterProcessor;
import com.example.core.processor.auth.LoginProcessorImpl;
import com.example.core.processor.auth.RegisterProcessorImpl;
import com.example.rest.jwt.JwtTokenUtil;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AuthController {

    private final LoginProcessor loginProcessor;
    private final RegisterProcessor registerProcessor;

    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(
            LoginProcessorImpl loginProcessor,
            RegisterProcessorImpl registerProcessor,
            JwtTokenUtil jwtTokenUtil) {

        this.loginProcessor = loginProcessor;
        this.registerProcessor = registerProcessor;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Either<Error, LoginResult> response = loginProcessor.process(loginRequest);

        if (response.isLeft()) {
            return ResponseEntity
                    .status(response.getLeft().getCode())
                    .body(response.getLeft().getMessage());
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(LoginResponse
                        .builder()
                        .jwttoken(jwtTokenUtil.generateToken(response.get().getUsername()))
                        .build());
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        Either<Error, RegisterResult> response = registerProcessor.process(registerRequest);

        if (response.isLeft()) {
            return ResponseEntity.
                    status(response.getLeft().getCode())
                    .body(response.getLeft().getMessage());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(RegisterResponse
                        .builder()
                        .jwttoken(jwtTokenUtil.generateToken(response.get().getUsername()))
                        .build());
    }

    @GetMapping(value = "/authTest")
    public void test(Principal principal) {
        // remember to set the Authorization header starting with Bearer
        System.out.println(principal.getName());
    }
}
