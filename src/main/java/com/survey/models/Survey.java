package com.survey.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table( name = "survey" )
public class Survey
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "survey_id" )
    private Long id;
    private String name;
    private String description;
    private Boolean editable;

    @Column( name = "creation_date", insertable = false)
    private Date creationDate;
    @ManyToMany( mappedBy = "surveys" )
    private List<User> users;
    @OneToMany( targetEntity = Question.class, mappedBy = "survey" )
    private List<Question> questions;


    public List<Question> getQuestions()
    {
        return questions;
    }


    public void setQuestions( List<Question> questions )
    {
        this.questions = questions;
    }


    public List<User> getUsers()
    {
        return users;
    }


    public void setUsers( List<User> users )
    {
        this.users = users;
    }


    public Long getId()
    {
        return id;
    }


    public void setId( Long id )
    {
        this.id = id;
    }


    public String getDescription()
    {
        return description;
    }


    public void setDescription( String description )
    {
        this.description = description;
    }


    public Boolean getEditable()
    {
        return editable;
    }


    public void setEditable( Boolean editable )
    {
        this.editable = editable;
    }


    public Date getCreationDate()
    {
        return creationDate;
    }


    public void setCreationDate( Date creationDate )
    {
        this.creationDate = creationDate;
    }


    public String getName()
    {
        return name;
    }


    public void setName( String name )
    {
        this.name = name;
    }
}
