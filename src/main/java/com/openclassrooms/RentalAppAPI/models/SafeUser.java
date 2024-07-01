package com.openclassrooms.RentalAppAPI.models;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SafeUser 
{
    private Integer id;
    private String email;
    private String name;
    @JsonProperty("created_at") private Timestamp createdAt;
    @JsonProperty("updated_at") private Timestamp updatedAt;
}
