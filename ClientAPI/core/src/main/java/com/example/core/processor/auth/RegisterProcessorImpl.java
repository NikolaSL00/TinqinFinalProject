package com.example.core.processor.auth;

import com.example.api.base.Error;
import com.example.api.erroring.error.FeignServiceError;
import com.example.api.erroring.error.UserAlreadyExistError;
import com.example.api.erroring.exception.UserCreationFailureException;
import com.example.api.model.auth.RegisterRequest;
import com.example.api.model.auth.RegisterResult;
import com.example.api.operation.RegisterProcessor;
import com.example.domain.feignClientUsers.service.UsersServiceFeignClientImpl;
import feignClientUsers.model.UserDTOResponse;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class RegisterProcessorImpl implements RegisterProcessor {
    private final UsersServiceFeignClientImpl userServiceFeignClient;

    public RegisterProcessorImpl(UsersServiceFeignClientImpl userServiceFeignClient) {
        this.userServiceFeignClient = userServiceFeignClient;
    }

    @Override
    public Either<Error, RegisterResult> process(RegisterRequest req) {

        return Try.of(()->{
                    final UserDTOResponse user = userServiceFeignClient.createUser(req);

                    UserDetails userDetails = userServiceFeignClient.findByUsername(user.getUsername());

                    return RegisterResult
                            .builder()
                            .username(userDetails.getUsername())
                            .build();

                }).toEither()
                .mapLeft(throwable -> {

                    if(throwable instanceof UserCreationFailureException){
                        return new UserAlreadyExistError();
                    }
                    return new FeignServiceError();
                });
    }
}
