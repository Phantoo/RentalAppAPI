package com.openclassrooms.RentalAppAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.RentalAppAPI.models.User;

public interface UserRepository extends JpaRepository<User, Integer>
{
    public User findByEmail(String email);
}
