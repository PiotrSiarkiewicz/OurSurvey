package com.survey.models;

import java.util.ArrayList;
import java.util.List;


public class QuestionLite
{
    private Question question;
    private List<ResultData> resultDatas;


    public QuestionLite( Question question )
    {
        this.question = question;
        this.resultDatas = new ArrayList<>();
    }


    public Question getQuestion()
    {
        return question;
    }


    public void setQuestion( Question question )
    {
        this.question = question;
    }


    public List<ResultData> getResultDatas()
    {
        return resultDatas;
    }


    public void setResultDatas( List<ResultData> resultDatas )
    {
        this.resultDatas = resultDatas;
    }
}
