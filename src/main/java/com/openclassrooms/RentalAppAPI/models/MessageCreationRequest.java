package com.openclassrooms.RentalAppAPI.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MessageCreationRequest 
{
    @JsonProperty(value = "user_id")
    private Integer user_id;

    @JsonProperty(value = "rental_id")
    private Integer rental_id;

    private String message;
}
