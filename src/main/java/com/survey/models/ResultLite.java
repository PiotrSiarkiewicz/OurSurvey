package com.survey.models;

import org.springframework.web.multipart.MultipartFile;


public class ResultLite
{
    private Long questionId;
    private Long answerId;
    private String text;



    public ResultLite( Long questionId, Long answerId, String text )
    {
        this.questionId = questionId;
        this.answerId = answerId;
        this.text = text;
    }


    public ResultLite()
    {
    }




    public Long getQuestionId()
    {
        return questionId;
    }


    public void setQuestionId( Long questionId )
    {
        this.questionId = questionId;
    }


    public Long getAnswerId()
    {
        return answerId;
    }


    public void setAnswerId( Long answerId )
    {
        this.answerId = answerId;
    }


    public String getText()
    {
        return text;
    }


    public void setText( String text )
    {
        this.text = text;
    }
}
