package com.openclassrooms.RentalAppAPI.models;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RentalCreationRequest 
{
    private String name;
    private BigDecimal surface;
    private BigDecimal price;
    private MultipartFile picture;
    private String description;
}
