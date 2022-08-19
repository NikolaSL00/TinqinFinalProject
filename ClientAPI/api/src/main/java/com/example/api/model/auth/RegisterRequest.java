package com.example.api.model.auth;


import com.example.api.base.OperationInput;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest implements OperationInput {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Integer age;
}
