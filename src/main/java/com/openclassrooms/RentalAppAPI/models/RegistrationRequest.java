package com.openclassrooms.RentalAppAPI.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegistrationRequest 
{
    private String name;
    private String email;
    private String password;
}
