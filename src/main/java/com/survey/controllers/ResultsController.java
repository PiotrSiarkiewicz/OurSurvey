package com.survey.controllers;

import com.survey.models.*;
import com.survey.repository.ResultRepository;
import com.survey.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Controller
public class ResultsController
{

    private SurveyRepository surveyRepository;
    private ResultRepository resultRepository;
    private Survey survey;
    private List<ResultData> resultDatas;

    @Autowired
    public ResultsController( SurveyRepository surveyRepository,ResultRepository resultRepository )
    {
        this.surveyRepository = surveyRepository;
        this.resultRepository = resultRepository;
    }


    @RequestMapping( value = "/surveys/results/{surveyId}", method = RequestMethod.GET )
    public String getResults( Model model, @PathVariable Long surveyId )
    {
        survey = surveyRepository.findSurveyById( surveyId );
        model.addAttribute( "survey", survey );
        return "results";
    }


    @RequestMapping( value = "/surveys/results/showdata/{resultId}", method = RequestMethod.GET )
    public String showResultData( Model model, @PathVariable Long resultId )
    {
        Result result = resultRepository.findResultById( resultId );
        List<QuestionLite> questionLiteList = new ArrayList<>();
        resultDatas = result.getResultDatas();
        for( Question question:
             survey.getQuestions())
        {
            QuestionLite questionLite = new QuestionLite( question );

            for( ResultData resultData :
                            resultDatas)
            {
                if(resultData.getQuestion_id().equals( question.getId() ) )
                {
                    questionLite.getResultDatas().add( resultData );
                }
            }
            questionLiteList.add(questionLite);
        }
        model.addAttribute( "questions", questionLiteList );
        return "show_result_data";
    }


    @RequestMapping( value = "/surveys/results/delete/{resultId}", method = RequestMethod.POST )
    public String deleteResult( Model model, @PathVariable Long resultId )
    {
        resultRepository.delete( resultId );
        return "redirect:/surveys/results/"+survey.getId();
    }

    @RequestMapping( value = "/surveys/results/displayImage/{resultDataId}")
    public String returnImage( Model model, @PathVariable Long resultDataId)
    {
        byte[] imageArray = new byte[0];
        for( ResultData resultData :
                        resultDatas )
        {
            if( resultData.getId().equals( resultDataId ) )
            {
                imageArray = resultData.getImage();
            }
        }
        byte[] byte64 = Base64.getEncoder().encode( imageArray );
        String base64Encoded = new String(byte64);
        return base64Encoded;
    }
}
