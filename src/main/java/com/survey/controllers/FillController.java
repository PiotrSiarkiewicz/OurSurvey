package com.survey.controllers;

import com.survey.models.*;
import com.survey.repository.ResultDataRepository;
import com.survey.repository.ResultRepository;
import com.survey.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Controller
public class FillController
{
    private Survey survey;
    private SurveyService surveyService;
    private ResultRepository resultRepository;
    private ResultDataRepository resultDataRepository;
    private Map<Long, byte[]> images;


    @Autowired
    public FillController(
                    SurveyService surveyService,
                    ResultRepository resultsRepository,
                    ResultDataRepository resultDataRepository )
    {
        this.surveyService = surveyService;
        this.resultRepository = resultsRepository;
        this.resultDataRepository = resultDataRepository;
    }


    @RequestMapping( value = "/surveys/fill/{surveyId}" )
    public String fill( @PathVariable Long surveyId, Model model )
    {
        survey = surveyService.findSurveyById( surveyId, model );
        List<ResultLite> results = new ArrayList<>();
        images = new HashMap<>();

        for( Iterator<Question> iterator = survey.getQuestions().iterator(); iterator.hasNext(); )
        {
            Question question = iterator.next();
            for( Iterator<Answer> iterator2 = question.getAnswers().iterator(); iterator2.hasNext(); )
            {
                ResultLite resultLite = new ResultLite();
                resultLite.setAnswerId( iterator2.next().getId() );
                resultLite.setQuestionId( question.getId() );
                results.add( resultLite );
            }
        }
        model.addAttribute( "results", results );
        return "fill";
    }


    @RequestMapping( value = "/surveys/fill/results", method = RequestMethod.POST )
    @ResponseStatus( value = HttpStatus.OK )
    public void results( @RequestBody ResultLite[] results )
    {
        Result result = new Result();
        createResult( result );
        for( int i = 0; i < results.length; i++ )
        {
            ResultData resulData = new ResultData(
                            results[i].getText(),
                            images.get( results[i].getAnswerId() ),
                            result,
                            results[i].getQuestionId()
            );
            if(resulData.getText() != null && !resulData.getText().equals( "false" ) )
            {
              resultDataRepository.save( resulData ) ;
            }
        }
    }


    private void createResult( Result result )
    {
        result.setSurvey( survey );
        result.setUser( surveyService.getLoggedUser() );
        resultRepository.save( result );
    }


    @RequestMapping( value = "/surveys/fill/upload_image/{id}", method = RequestMethod.POST )
    @ResponseStatus( value = HttpStatus.OK )
    public void resultsImage( @RequestParam( value = "file" ) MultipartFile file, @PathVariable Long id )
    {
        byte[] image;
        try
        {
            image = file.getBytes();
            images.put( id, image );
        }
        catch( IOException e )
        {
            System.err.println( "Convert image failed" );
        }
    }
}

