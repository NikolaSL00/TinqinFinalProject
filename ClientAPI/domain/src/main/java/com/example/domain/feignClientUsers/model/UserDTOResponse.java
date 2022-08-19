package com.example.domain.feignClientUsers.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTOResponse {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Integer age;
}
