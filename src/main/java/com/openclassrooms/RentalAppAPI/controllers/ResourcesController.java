package com.openclassrooms.RentalAppAPI.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourcesController 
{
    	@GetMapping("/")
	    public String getResource() 
	    {
	        return "value";
	    } 
}
