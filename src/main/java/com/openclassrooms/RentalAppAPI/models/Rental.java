package com.openclassrooms.RentalAppAPI.models;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rentals")
@Getter @Setter
public class Rental 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private BigDecimal surface;
    
    private BigDecimal price;

    @Column(name = "picture")
    private String pictureUrl;
    
    @Column(length = 2000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id")//, referencedColumnName = "id")
    private User owner;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
