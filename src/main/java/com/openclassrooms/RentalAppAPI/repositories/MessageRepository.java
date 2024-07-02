package com.openclassrooms.RentalAppAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.RentalAppAPI.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {}
