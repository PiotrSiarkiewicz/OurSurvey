package com.survey.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
public class User
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "user_id" )
    private int id;

    @Column( name = "email", nullable = false, unique = true )
    @Email( message = "Please provide a valid e-mail" )
    @NotEmpty( message = "Please provide an e-mail" )
    private String email;
    private String password;
    @NotEmpty( message = "Please provide your username" )
    private String username;
    @Column( name = "confirmation_token" )
    private String confirmationToken;
    private boolean enabled;
    @ManyToMany @JoinTable( name = "access", joinColumns = @JoinColumn( name = "user_id" ), inverseJoinColumns = @JoinColumn( name = "survey_id" ) )
    private List<Survey> surveys;
    @OneToMany( targetEntity = Result.class, mappedBy = "user" )
    private List<Result> results;


    public void setUsername( String username )
    {
        this.username = username;
    }


    public boolean isEnabled()
    {
        return enabled;
    }


    public List<Result> getResults()
    {
        return results;
    }


    public void setResults( List<Result> results )
    {
        this.results = results;
    }


    public List<Survey> getSurveys()
    {
        return surveys;

    }

    public void setSurveys( List<Survey> surveys )
    {
        this.surveys = surveys;
    }

    public User()
    {
    }


    public User( User user )
    {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.id = user.getId();
        this.password = user.getPassword();
    }


    public String getConfirmationToken()
    {
        return confirmationToken;
    }


    public void setConfirmationToken( String confirmationToken )
    {
        this.confirmationToken = confirmationToken;
    }


    public boolean getEnabled()
    {
        return enabled;
    }


    public void setEnabled( boolean value )
    {
        this.enabled = value;
    }


    public int getId()
    {
        return id;
    }


    public void setId( int id )
    {
        this.id = id;
    }


    public String getEmail()
    {
        return email;
    }


    public void setEmail( String email )
    {
        this.email = email;
    }


    public String getPassword()
    {
        return password;
    }


    public void setPassword( String password )
    {
        this.password = password;
    }


    public String getUsername()
    {
        return username;
    }


    public void setusername( String username )
    {
        this.username = username;
    }
}

