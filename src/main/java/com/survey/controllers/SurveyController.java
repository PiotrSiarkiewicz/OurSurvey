package com.survey.controllers;

import com.survey.models.*;
import com.survey.repository.SurveyRepository;
import com.survey.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;


@Controller
public class SurveyController
{

    private UserRepository userRepository;
    private SurveyRepository surveyRepository;


    @Autowired
    SurveyController( UserRepository userRepository, SurveyRepository surveyRepository )
    {
        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
    }


    @RequestMapping( value = "/surveys", method = RequestMethod.GET )
    public String main( Model model, Survey survey )
    {
        List<Survey> surveys = getSurveysFromLoggedUser();

        model.addAttribute( "surveys", surveys );
        model.addAttribute( "survey", survey );
        return "surveys";
    }


    @RequestMapping( value = "/surveys/delete/{surveyId}" )
    public ModelAndView delete( @PathVariable Long surveyId )
    {
        removeSurveyFromSurveyList( surveyId );
        surveyRepository.delete( surveyId );

        return new ModelAndView( "redirect:/surveys" );
    }


    @RequestMapping( value = "/surveys/fill/{surveyId}" )
    public ModelAndView fill( @PathVariable Long surveyId, Model model )
    {
        findSurveyById( surveyId, model );

        return new ModelAndView( "fill" );
    }


    @RequestMapping( value = "/surveys/edit/{surveyId}", method = RequestMethod.GET )
    public ModelAndView edit(@PathVariable Long surveyId, Model model)
    {
        findSurveyById( surveyId, model );
        return new ModelAndView( "edit" );
    }


    private void findSurveyById( @PathVariable Long surveyId, Model model )
    {
        List<Survey> surveys = getSurveysFromLoggedUser();
        for( Iterator<Survey> iterator = surveys.iterator(); iterator.hasNext(); )
        {
            Survey survey = iterator.next();
            if( survey.getId().equals( surveyId ) )
            {
                model.addAttribute( "survey", survey );
            }
        }
    }

    private void removeSurveyFromSurveyList( @PathVariable Long surveyId )
    {
        List<Survey> surveys = getSurveysFromLoggedUser();
        surveys.removeIf( survey -> survey.getId().equals( surveyId ) );
    }


    private List<Survey> getSurveysFromLoggedUser()
    {
        UserDetailsImpl userDetails =
                        (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername( userDetails.getUsername() );
        return new ArrayList<>( user.get().getSurveys() );
    }
}


