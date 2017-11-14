package com.survey.controllers;

import com.survey.models.*;
import com.survey.repository.SurveyRepository;
import com.survey.repository.UserRepository;
import com.survey.services.SurveyService;
import com.survey.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;


@Controller
public class SurveyController
{

    private SurveyService surveyService;
    private UserDetailsServiceImpl userDetailsService;
    private UserRepository userRepository;
    private SurveyRepository surveyRepository;


    @Autowired
    SurveyController(
                    SurveyRepository surveyRepository,
                    SurveyService surveyService,
                    UserDetailsServiceImpl userDetailsService, UserRepository userRepository )
    {
        this.surveyRepository = surveyRepository;
        this.surveyService = surveyService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }


    @RequestMapping( value = "/surveys", method = RequestMethod.GET )
    public String main( Model model, Survey survey )
    {
        List<Survey> surveys = surveyService.getSurveysFromLoggedUser();

        model.addAttribute( "surveys", surveys );
        model.addAttribute( "survey", survey );
        return "surveys";
    }


    @RequestMapping( value = "/surveys/delete/{surveyId}" )
    public ModelAndView delete( @PathVariable Long surveyId )
    {
        surveyService.removeSurveyFromSurveyList( surveyId );
        surveyRepository.delete( surveyId );

        return new ModelAndView( "redirect:/surveys" );
    }


    @RequestMapping( value = "/surveys/create", method = RequestMethod.POST )
    public ModelAndView create(
                    @Valid Survey survey )
    {
        User user = surveyService.getLoggedUser();
        surveyService.prepareSurveyAndUserToSave( survey, user );
        surveyRepository.save( survey );
        userDetailsService.saveUser( user );
        return new ModelAndView( "redirect:/surveys" );
    }
}


