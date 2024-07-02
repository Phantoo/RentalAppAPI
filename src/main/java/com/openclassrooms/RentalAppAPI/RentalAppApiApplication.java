package com.openclassrooms.RentalAppAPI;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.openclassrooms.RentalAppAPI.services.FileStorageService;

import jakarta.annotation.Resource;

@SpringBootApplication
public class RentalAppApiApplication implements CommandLineRunner
{
	@Resource
	private FileStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(RentalAppApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception 
	{
		// Init storage folder
		storageService.init();
	}	
}
