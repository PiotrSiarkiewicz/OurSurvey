package com.survey.controllers;

import com.survey.models.*;
import com.survey.repository.SurveyRepository;
import com.survey.repository.UserRepository;
import com.survey.services.SurveyService;
import com.survey.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String main(
                    Model model,
                    Survey survey,
                    @ModelAttribute( "shared" ) final Object shared,
                    @ModelAttribute( "shareMessage" ) final Object shareMessage, UsernameForm username )
    {
        List<Survey> surveys = surveyService.getSurveysFromLoggedUser();
        model.addAttribute( "loggedUser", surveyService.getLoggedUser().getUsername() );
        model.addAttribute( "shareMessage", shareMessage );
        model.addAttribute( "shared", shared );
        model.addAttribute( "surveys", surveys );
        model.addAttribute( "survey", survey );
        model.addAttribute( "username", username );
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
        survey.setCreator( user.getUsername() );
        surveyService.prepareSurveyAndUserToSave( survey, user );
        surveyRepository.save( survey );
        userDetailsService.saveUser( user );
        return new ModelAndView( "redirect:/surveys" );
    }


    @RequestMapping( value = "/surveys/share/{surveyId}", method = RequestMethod.POST )
    public ModelAndView share( @PathVariable Long surveyId, UsernameForm username, RedirectAttributes model )
    {
        Survey survey = surveyRepository.findSurveyById( surveyId );
        Optional<User> user = userRepository.findByUsername( username.getUsername() );
        if( user.isPresent() )
        {
            surveyService.prepareSurveyAndUserToSave( survey, user.get() );
            surveyRepository.save( survey );
            userDetailsService.saveUser( user.get() );
            model.addFlashAttribute( "shared", "complete" );
            model.addFlashAttribute( "shareMessage", "Share complete!" );
        }
        else
        {
            model.addFlashAttribute( "shared", "error" );
            model.addFlashAttribute( "shareMessage", "There is no user with this username!" );
        }

        return new ModelAndView( "redirect:/surveys" );
    }
}

class UsernameForm
{
    private String username;

    public String getUsername()
    {
        return username;
    }


    public void setUsername( String username )
    {
        this.username = username;
    }
}


