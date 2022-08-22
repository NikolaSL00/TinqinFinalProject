package feignClientUsers.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Builder
public class UserDTOCreateRequest {
    @NotEmpty
    private final String username;
    @NotEmpty
    @Size(min = 4, message = "password should have at least 2 characters")
    private final String password;
    private final String firstName;
    private final String lastName;
    private final Integer age;

    public UserDTOCreateRequest(String username, String password, String firstName, String lastName, Integer age) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
