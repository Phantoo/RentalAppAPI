package com.openclassrooms.RentalAppAPI.models;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RentalUpdateRequest 
{
    private String name;
    private BigDecimal surface;
    private BigDecimal price;
    private String description;
}
