package com.openclassrooms.RentalAppAPI.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.RentalAppAPI.models.SafeUser;
import com.openclassrooms.RentalAppAPI.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("user")
public class UsersController 
{
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @Operation(responses = {
        @ApiResponse(responseCode = "200", 
                    description = "Fetch user corresponding to the specified id", 
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(implementation = SafeUser.class))),
        @ApiResponse(responseCode = "401",
                    description = "Authentication failure",
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(type = "object")))
    })
    public ResponseEntity<?> get(@PathVariable Integer id)
    {
        SafeUser user = userService.getSafeById(id);
        return ResponseEntity.ok(user);
    }
}
