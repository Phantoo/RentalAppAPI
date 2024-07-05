package com.openclassrooms.RentalAppAPI.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.openclassrooms.RentalAppAPI.models.Rental;
import com.openclassrooms.RentalAppAPI.models.RentalCreationRequest;
import com.openclassrooms.RentalAppAPI.models.RentalResponse;
import com.openclassrooms.RentalAppAPI.models.RentalUpdateRequest;
import com.openclassrooms.RentalAppAPI.models.User;
import com.openclassrooms.RentalAppAPI.services.FileStorageService;
import com.openclassrooms.RentalAppAPI.services.RentalService;
import com.openclassrooms.RentalAppAPI.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/rentals")
public class RentalsController 
{
    @Autowired
    private RentalService rentalService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService storageService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    @Operation(responses = {
        @ApiResponse(responseCode = "200", 
                    description = "Fetch all rentals", 
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(type = "object", properties = { @StringToClassMapItem(key = "rentals", value = ArrayList.class) }))),
        @ApiResponse(responseCode = "401",
                    description = "Authentication failure",
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(type = "object")))
    })
    public ResponseEntity<?> getAll() 
    {
        List<RentalResponse> rentals =  rentalService.findAll()
            .stream()
            .map(r -> modelMapper.map(r, RentalResponse.class))
            .collect(Collectors.toList());        
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
        Rental rental = rentalService.getById(id);
        RentalResponse response = modelMapper.map(rental, RentalResponse.class);
        return ResponseEntity.ok(response);
    }




    @PostMapping(consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(responses = {
        @ApiResponse(responseCode = "200", 
                    description = "Rental creation success", 
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(type = "object", properties = { @StringToClassMapItem(key = "message", value = String.class) }))),
        @ApiResponse(responseCode = "401",
                    description = "Authentication failure",
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(type = "object")))
    })
    public ResponseEntity<?> add(@ModelAttribute RentalCreationRequest request, Authentication authentication, UriComponentsBuilder ucb) 
    {
        // Get request owner
        User owner = userService.get(authentication.getName());

        // Upload picture and get its url
        MultipartFile file = request.getPicture();
        storageService.save(file);
        String url = ucb.path(storageService.folderName + "/" + file.getOriginalFilename()).build().toUriString();

        // Add Rental
        rentalService.add(request, owner, url);
        return ResponseEntity.ok(Map.of("message", "Rental created !"));
    }
    



    @PutMapping(path = "/{id}", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(responses = {
        @ApiResponse(responseCode = "200", 
                    description = "Rental update success", 
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(type = "object", properties = { @StringToClassMapItem(key = "message", value = String.class) }))),
        @ApiResponse(responseCode = "401",
                    description = "Authentication failure",
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(type = "object")))
    })
    public ResponseEntity<?> update(@PathVariable Integer id, @ModelAttribute RentalUpdateRequest request) 
    {
        rentalService.update(request, id);
        return ResponseEntity.ok(Map.of("message", "Rental updated !"));
    }
}
