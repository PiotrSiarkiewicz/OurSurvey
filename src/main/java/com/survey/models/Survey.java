package com.survey.models;


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
    private String creator;
    @Column( name = "creation_date", insertable = false)
    private Date creationDate;
    @ManyToMany( mappedBy = "surveys" )
    private List<User> users;
    @OneToMany( targetEntity = Question.class, mappedBy = "survey", fetch = FetchType.EAGER)
    private List<Question> questions;
    @OneToMany( targetEntity = Result.class, mappedBy = "survey" )
    private List<Result> results;

    public List<Question> getQuestions()
    {
        return questions;
    }


    public List<Result> getResults()
    {
        return results;
    }


    public void setResults( List<Result> results )
    {
        this.results = results;
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


    public String getCreator()
    {
        return creator;
    }


    public void setCreator( String creator )
    {
        this.creator = creator;
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
