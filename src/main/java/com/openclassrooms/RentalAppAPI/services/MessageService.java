package com.openclassrooms.RentalAppAPI.services;

import java.sql.Timestamp;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.RentalAppAPI.models.Message;
import com.openclassrooms.RentalAppAPI.models.MessageCreationRequest;
import com.openclassrooms.RentalAppAPI.models.Rental;
import com.openclassrooms.RentalAppAPI.models.User;
import com.openclassrooms.RentalAppAPI.repositories.MessageRepository;

@Service
public class MessageService 
{
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RentalService rentalService;

    @Autowired
    private ModelMapper modelMapper;

    public Message add(MessageCreationRequest request) throws NotFoundException
    {
        Message message = modelMapper.map(request, Message.class);

        // Set user
        User user = userService.getById(request.getUser_id());
        if (user == null)
            throw new NotFoundException();
        message.setUser(user);

        // Set rental
        Rental rental = rentalService.getById(request.getRental_id());
        if (rental == null)
            throw new NotFoundException();
        message.setRental(rental);

        // Set dates
        Date now = new Date();
        message.setCreatedAt(new Timestamp(now.getTime()));
        message.setUpdatedAt(new Timestamp(now.getTime()));

        Message addedMessage = messageRepository.save(message);
        return addedMessage;
    }
}
