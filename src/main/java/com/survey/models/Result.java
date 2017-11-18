package com.survey.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
public class Result
{
    @Id
    @Column(name = "result_id")
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long id;
    @Column( name = "fill_date", insertable = false)
    private Date fillDate;
    @ManyToOne
    @JoinColumn (name = "survey_id")
    private Survey survey;
    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;
    @OneToMany( targetEntity = ResultData.class, mappedBy = "result" )
    private List<ResultData> resultdatas;


    public List<ResultData> getResultdatas()
    {
        return resultdatas;
    }


    public void setResultdatas( List<ResultData> resultdatas )
    {
        this.resultdatas = resultdatas;
    }


    public Long getId()
    {
        return id;
    }


    public void setId( Long id )
    {
        this.id = id;
    }


    public Date getFillDate()
    {
        return fillDate;
    }


    public void setFillDate( Date fillDate )
    {
        this.fillDate = fillDate;
    }


    public Survey getSurvey()
    {
        return survey;
    }


    public void setSurvey( Survey survey )
    {
        this.survey = survey;
    }


    public User getUser()
    {
        return user;
    }


    public void setUser( User user )
    {
        this.user = user;
    }
}
