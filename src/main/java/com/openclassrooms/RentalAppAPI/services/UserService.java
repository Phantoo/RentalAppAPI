package com.openclassrooms.RentalAppAPI.services;

import java.sql.Timestamp;
import java.util.Date;

import javax.naming.AuthenticationException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.RentalAppAPI.models.User;
import com.openclassrooms.RentalAppAPI.models.SafeUser;
import com.openclassrooms.RentalAppAPI.models.RegistrationRequest;
import com.openclassrooms.RentalAppAPI.repositories.UserRepository;

@Service
public class UserService 
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    // Returns true only if the username/password matches an existing account
    public Boolean isValid(String username, String password)
    {
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } 
        catch (UsernameNotFoundException e) {
            return false;
        }

        return passwordEncoder.matches(password, userDetails.getPassword());
    }

    // Create and Save User from RegistrationRequest
    public User add(RegistrationRequest request) throws AuthenticationException
    {
        User user = modelMapper.map(request, User.class);

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);

        Date now = new Date();
        user.setCreatedAt(new Timestamp(now.getTime()));
        user.setUpdatedAt(new Timestamp(now.getTime()));

        // Make sure that the used email is unique
        User foundUser = userRepository.findByEmail(user.getEmail());
        if (foundUser != null)
            throw new AuthenticationException();

        // Save and return user
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    // Returns a User corresponding to the specified id
    public User getById(Integer id)
    {
        return userRepository.findById(id).orElse(null);
    }

    // Returns a User corresponding to the specified username
    public User get(String username)
    {
        return userRepository.findByEmail(username);
    }

    // Returns a HiddenPasswordUser depending on the specified username
    public SafeUser getSafe(String username)
    {
        User user = userRepository.findByEmail(username);
        SafeUser safeUser = modelMapper.map(user, SafeUser.class);
        return safeUser;
    }
}
