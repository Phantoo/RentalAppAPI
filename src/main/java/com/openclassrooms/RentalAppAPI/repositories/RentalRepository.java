package com.openclassrooms.RentalAppAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.RentalAppAPI.models.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {}
