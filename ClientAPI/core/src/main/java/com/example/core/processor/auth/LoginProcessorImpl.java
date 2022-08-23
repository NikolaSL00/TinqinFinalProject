package com.example.core.processor.auth;

import com.example.api.base.Error;
import com.example.api.erroring.error.FeignServiceError;
import com.example.api.erroring.error.UserNotFoundError;
import com.example.api.model.auth.LoginRequest;
import com.example.api.model.auth.LoginResult;
import com.example.api.operation.LoginProcessor;
import com.example.domain.feignClientUsers.service.UsersServiceFeignClientImpl;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginProcessorImpl  implements LoginProcessor {



    private final UsersServiceFeignClientImpl usersServiceFeignClient;

    public LoginProcessorImpl(UsersServiceFeignClientImpl usersServiceFeignClient) {
        this.usersServiceFeignClient = usersServiceFeignClient;
    }

    @Override
    public Either<Error, LoginResult> process(LoginRequest req) {

        return Try.of(()->{
                    UserDetails user = usersServiceFeignClient.findByUsername(req.getUsername());

                    return LoginResult.builder()
                            .username(user.getUsername())
                            .build();
                }).toEither()
                .mapLeft(throwable -> {

                    if(throwable instanceof UsernameNotFoundException){
                        return new UserNotFoundError();
                    }
                    return new FeignServiceError();
                });
    }
}
