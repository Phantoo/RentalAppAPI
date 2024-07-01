package com.openclassrooms.RentalAppAPI.models;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest
{
    @JsonAlias("login")
    private String username;
    private String password;
}
