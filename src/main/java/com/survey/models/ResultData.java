package com.survey.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;


@Entity
public class ResultData
{
    @Id
    @Column( name = "resultdata_id" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;
    private String text;
    @Lob
    @Column( name = "image", columnDefinition = "mediumblob" )
    private byte[] image;
    @ManyToOne
    @JoinColumn( name = "result_id" )
    private Result result;
    private Long question_id;

    public ResultData( String text, byte[] image, Result result,  Long question_id )
    {
        this.text = text;
        this.image = image;
        this.result = result;
        this.question_id = question_id;
    }


    public ResultData()
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


    public byte[] getImage()
    {
        return image;
    }


    public void setImage( byte[] image )
    {
        this.image = image;
    }


    public Result getResult()
    {
        return result;
    }


    public void setResult( Result result )
    {
        this.result = result;
    }


    public Long getQuestion_id()
    {
        return question_id;
    }


    public void setQuestion_id( Long question_id )
    {
        this.question_id = question_id;
    }
}
