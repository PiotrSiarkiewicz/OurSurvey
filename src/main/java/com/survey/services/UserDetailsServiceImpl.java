package com.survey.services;

import com.survey.models.UserDetailsImpl;
import com.survey.models.User;
import com.survey.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserDetailsServiceImpl
                implements UserDetailsService
{

    private UserRepository userRepository;


    @Autowired
    public UserDetailsServiceImpl( UserRepository userRepository)

    {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetailsImpl loadUserByUsername( String username ) throws UsernameNotFoundException
    {
        Optional<User> optionalUser = userRepository.findByUsername( username );
        if( !optionalUser.isPresent() )
        {
            return null;
        }
        return optionalUser
                        .map( UserDetailsImpl::new ).get();
    }


    public User findByConfirmationToken( String confirmationToken )
    {
        return userRepository.findByConfirmationToken( confirmationToken );
    }


    public void saveUser( User user )
    {
        userRepository.save( user );
    }
}
