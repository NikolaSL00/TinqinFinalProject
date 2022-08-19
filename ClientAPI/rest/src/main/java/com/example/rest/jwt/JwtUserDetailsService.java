package com.example.rest.jwt;

import com.example.domain.feignClientUsers.service.UsersServiceFeignClientImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UsersServiceFeignClientImpl usersServiceFeignClient;

    public JwtUserDetailsService(UsersServiceFeignClientImpl usersServiceFeignClient) {
        this.usersServiceFeignClient = usersServiceFeignClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return usersServiceFeignClient.findByUsername(username);
    }


}
