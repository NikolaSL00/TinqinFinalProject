package com.example.api.model.auth;

import com.example.api.base.OperationResult;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse implements OperationResult {
    private String jwttoken;
}
