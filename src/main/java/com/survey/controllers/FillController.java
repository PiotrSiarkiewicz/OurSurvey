package com.survey.controllers;

import com.survey.models.Survey;
import com.survey.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FillController
{
    private Survey survey;
    private SurveyService surveyService;


    @Autowired
    public FillController( SurveyService surveyService)
    {
        this.surveyService = surveyService;
    }


    @RequestMapping( value = "/surveys/fill/{surveyId}" )
    public ModelAndView fill( @PathVariable Long surveyId, Model model )
    {
        survey = surveyService.findSurveyById( surveyId, model );
        return new ModelAndView( "fill" );
    }
}
