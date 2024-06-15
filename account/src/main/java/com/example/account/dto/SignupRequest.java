package com.example.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 8, max = 40)
    private String password;

    private Set<String> role;

    @NotBlank
    private String branchAddress;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String mobileNumber;

    private Gender gender;

}

enum Gender{
    MALE,FEMALE,OTHERS;
}