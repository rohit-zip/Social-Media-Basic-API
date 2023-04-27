package com.api.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class userDto {

    private int id;

    @NotEmpty(message = "Name cannot be Empty")
    @Size(min = 2, max = 20, message = "Name must be greater than 2 and less than 20")
    private String name;

    @NotEmpty
    @Email(message = "Please enter valid Email")
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String about;
}
