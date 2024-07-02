package com.openclassrooms.RentalAppAPI.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.RentalAppAPI.models.MessageCreationRequest;
import com.openclassrooms.RentalAppAPI.services.MessageService;

import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/messages")
public class MessagesController 
{
    @Autowired
    private MessageService messageService;

    @PostMapping()
    @Operation(responses = {
        @ApiResponse(responseCode = "200", 
                    description = "Message creation success", 
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(type = "object", properties = { @StringToClassMapItem(key = "message", value = String.class) }))),
        @ApiResponse(responseCode = "400",
                    description = "Message creation failure",
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(type = "object"))),
        @ApiResponse(responseCode = "401",
                    description = "Authentication failure",
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(type = "object")))
    })
    public ResponseEntity<?> add(@RequestBody MessageCreationRequest request) 
    {
        // Empty model -> BadRequest
        if (StringUtils.isBlank(request.getMessage()) ||
            request.getRental_id() == 0 ||
            request.getUser_id() == 0)
            return ResponseEntity.badRequest().body(Map.of());

        // Add Message
        try {
            messageService.add(request);
        } 
        catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(Map.of());
        }
        return ResponseEntity.ok(Map.of("message", "Message sent with success"));
    }
}
