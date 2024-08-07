package com.openclassrooms.RentalAppAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.RentalAppAPI.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    public User findByEmail(String email);
}
