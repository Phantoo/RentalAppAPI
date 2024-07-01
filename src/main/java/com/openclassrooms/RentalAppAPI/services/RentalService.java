package com.openclassrooms.RentalAppAPI.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.RentalAppAPI.models.Rental;
import com.openclassrooms.RentalAppAPI.models.RentalRequest;
import com.openclassrooms.RentalAppAPI.repositories.RentalRepository;

@Service
public class RentalService 
{
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Rental> findAll() 
    {
        return rentalRepository.findAll();
    }

    public Rental findById(Integer id)
    {
        return rentalRepository.findById(id).orElse(null);
    }

    public Rental add(RentalRequest request)
    {
        Rental rental = modelMapper.map(request, Rental.class);

        Date now = new Date();
        rental.setCreatedAt(new Timestamp(now.getTime()));
        rental.setUpdatedAt(new Timestamp(now.getTime()));

        Rental addedRental = rentalRepository.save(rental);
        return addedRental;
    }
}
