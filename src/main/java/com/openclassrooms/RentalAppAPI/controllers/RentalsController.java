package com.openclassrooms.RentalAppAPI.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.RentalAppAPI.models.Rental;
import com.openclassrooms.RentalAppAPI.models.RentalRequest;
import com.openclassrooms.RentalAppAPI.services.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/rentals")
public class RentalsController 
{
    @Autowired
    private RentalService rentalService;

    @GetMapping()
    @Operation(responses = {
        @ApiResponse(responseCode = "200", 
                    description = "Fetch all rentals", 
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(type = "object", properties = { @StringToClassMapItem(key = "rentals", value = List.class) }))),
        @ApiResponse(responseCode = "401",
                    description = "Authentication failure",
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(type = "object")))
    })
    public ResponseEntity<?> getAll() 
    {
        List<Rental> rentals = rentalService.findAll();
        return ResponseEntity.ok(Map.of("rentals", rentals));
    }
    


    @GetMapping("/{id}")
    @Operation(responses = {
        @ApiResponse(responseCode = "200", 
                    description = "Fetch rental corresponding to the specified id", 
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(implementation = Rental.class))),
        @ApiResponse(responseCode = "401",
                    description = "Authentication failure",
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(type = "object")))
    })
    public ResponseEntity<?> get(@PathVariable Integer id)
    {
        Rental rental = rentalService.findById(id);
        return ResponseEntity.ok(rental);
    }




    @PostMapping()
    public ResponseEntity<?> add(@RequestBody RentalRequest rental, Authentication authentication) 
    {
        // Get current authenticated user for the owner column
        String t = authentication.getUsername();

        // Add Rental
        try {
            rentalService.add(rental);
        } 
        catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of());
        }

        return ResponseEntity.ok(Map.of("message", "Rental created !"));
    }
    
}
