package com.openclassrooms.RentalAppAPI.models;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RentalResponse 
{
    private Integer id;
    private String name;
    private BigDecimal surface;
    private BigDecimal price;
    private String picture;
    private String description;
    @JsonProperty(value = "owner_id") private Integer ownerId;
    @JsonProperty(value = "created_at") private Timestamp createdAt;
    @JsonProperty(value = "updated_at") private Timestamp updatedAt;
}
