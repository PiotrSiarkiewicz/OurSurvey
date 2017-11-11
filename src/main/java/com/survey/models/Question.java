package com.survey.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;


@Entity
public class Question
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "question_id") @NotEmpty
    private Long id;
    private String text;

    @ManyToOne
    @JoinColumn (name = "survey_id")
    private Survey survey;

    @OneToMany( targetEntity = Answer.class, mappedBy = "question")
    private List<Answer> answers;


    public List<Answer> getAnswers()
    {
        return answers;
    }


    public void setAnswers( List<Answer> answers )
    {
        this.answers = answers;
    }


    public Question()
    {

    }

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


    public Survey getSurvey()
    {
        return survey;
    }


    public void setSurvey( Survey survey )
    {
        this.survey = survey;
    }
}
