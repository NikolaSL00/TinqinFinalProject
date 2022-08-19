package com.example.domain.feignClientUsers.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTOFindRequest {
    private final String username;

    public UserDTOFindRequest(String username) {
        this.username = username;
    }
}
