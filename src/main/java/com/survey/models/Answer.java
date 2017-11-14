package com.survey.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;


@Entity
public class Answer
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "answer_id" )
    private Long id;
    private String text;
    private String type;

    @ManyToOne
    @JoinColumn (name = "question_id")
    private Question question;


    public Long getId()
    {
        return id;
    }


    public void setId( Long id )
    {
        this.id = id;
    }


    public String getText()
    {
        return text;
    }


    public void setText( String text )
    {
        this.text = text;
    }


    public String getType()
    {
        return type;
    }


    public void setType( String type )
    {
        this.type = type;
    }


    public Question getQuestion()
    {
        return question;
    }


    public void setQuestion( Question question )
    {
        this.question = question;
    }
}
