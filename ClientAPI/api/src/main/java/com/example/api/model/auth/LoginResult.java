package com.example.api.model.auth;

import com.example.api.base.OperationResult;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResult implements OperationResult {
    private String username;
}
