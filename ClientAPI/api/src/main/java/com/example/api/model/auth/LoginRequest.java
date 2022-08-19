package com.example.api.model.auth;

import com.example.api.base.OperationInput;
import lombok.*;


@Builder
@Data
public class LoginRequest implements OperationInput {
    private String username;
    private String password;
}
