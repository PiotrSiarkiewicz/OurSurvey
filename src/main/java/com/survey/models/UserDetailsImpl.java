package com.survey.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class UserDetailsImpl
                extends User
                implements UserDetails
{
    public UserDetailsImpl( final User user )
    {
        super( user );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }


    @Override
    public String getPassword()
    {
        return super.getPassword();
    }


    @Override
    public String getUsername()
    {
        return super.getUsername();
    }


    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }


    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }


    @Override
    public boolean isEnabled()
    {
        return true;
    }


    @Override
    public int getId()
    {
        return super.getId();
    }
}
