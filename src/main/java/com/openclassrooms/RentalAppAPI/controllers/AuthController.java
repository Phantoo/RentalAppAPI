package com.openclassrooms.RentalAppAPI.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.RentalAppAPI.models.SafeUser;
import com.openclassrooms.RentalAppAPI.models.LoginRequest;
import com.openclassrooms.RentalAppAPI.models.RegistrationRequest;
import com.openclassrooms.RentalAppAPI.services.UserDetailsService;
import com.openclassrooms.RentalAppAPI.services.JwtService;
import com.openclassrooms.RentalAppAPI.services.UserService;

import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/auth")
public class AuthController 
{
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    // Authenticate the specified username
    private Authentication authenticate(String username, String password)
    {
        Boolean loginValid = userService.isValid(username, password);
        if (loginValid == false)
            return null;

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
    }

    // Generate JWT token for the specified Authentication object
    private String generateToken(Authentication authentication)
    {
        String token = jwtService.generateToken(authentication);
        return token;
    }

    
    
    @PostMapping("/login")
    @Operation(responses = {
        @ApiResponse(responseCode = "200", 
                    description = "Login success", 
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(type = "object", properties = { @StringToClassMapItem(key = "token", value = String.class) }))),
        @ApiResponse(responseCode = "401",
                    description = "Login failure",
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(type = "object", properties = { @StringToClassMapItem(key = "message", value = String.class) })))
    })
    public ResponseEntity<?> login(@RequestBody LoginRequest request)
    {
        // Try logging the user in        
        Authentication auth = authenticate(request.getUsername(), request.getPassword());
        if (auth == null || auth.isAuthenticated() == false)
            return new ResponseEntity<>(Map.of("message", "error"), HttpStatus.UNAUTHORIZED);

        // Generate and return access token
        String token = generateToken(auth);
        return ResponseEntity.ok(Map.of("token", token));
    }



    
    @PostMapping("/register")
    @Operation(responses = {
        @ApiResponse(responseCode = "200", 
                    description = "Registration success", 
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(type = "object", properties = { @StringToClassMapItem(key = "token", value = String.class) }))),
        @ApiResponse(responseCode = "400", 
                    description = "Registration failure",
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(type = "object")))
    })
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest)
    {
        // Empty model -> BadRequest
        if (StringUtils.isBlank(registrationRequest.getName()) ||
            StringUtils.isBlank(registrationRequest.getEmail()) ||
            StringUtils.isBlank(registrationRequest.getPassword()))
            return ResponseEntity.badRequest().body(Map.of());

        // Registration
        try {
            userService.add(registrationRequest);
        } 
        // Exception -> BadRequest
        catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of());
        }

        // Authenticate the user and return the token
        Authentication auth = authenticate(registrationRequest.getEmail(), registrationRequest.getPassword());
        String token = generateToken(auth);
        return ResponseEntity.ok(Map.of("token", token));
    }




    @GetMapping("me")
    @Operation(responses = {
        @ApiResponse(responseCode = "200", 
                    description = "Authentication success", 
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(implementation = SafeUser.class))),
        @ApiResponse(responseCode = "401", 
                    description = "Authentication failure",
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(type = "object")))
    })
    public SafeUser me(Authentication authentication) 
    {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        return userService.getSafe(userDetails.getUsername());
    }
}
