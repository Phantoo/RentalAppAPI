package com.openclassrooms.RentalAppAPI.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest
{
    @JsonProperty(value = "email")
    private String username;
    private String password;
}
