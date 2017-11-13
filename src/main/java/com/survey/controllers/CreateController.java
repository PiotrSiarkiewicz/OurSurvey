package com.survey.controllers;

import com.survey.models.Survey;
import com.survey.models.User;
import com.survey.models.UserDetailsImpl;
import com.survey.repository.SurveyRepository;
import com.survey.repository.UserRepository;
import com.survey.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class CreateController
{
    private SurveyRepository surveyRepository;
    private UserDetailsServiceImpl userDetailsService;
    private UserRepository userRepository;


    @Autowired
    public CreateController(
                    SurveyRepository surveyRepository,
                    UserDetailsServiceImpl userDetailsService,
                    UserRepository userRepository )
    {
        this.surveyRepository = surveyRepository;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }


    @RequestMapping( value = "/surveys/create", method = RequestMethod.POST )
    public ModelAndView create(
                    @Valid Survey survey, Model model )
    {
        List<User> users = new ArrayList<>();
        UserDetailsImpl userDetails =
                        (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOptional = userRepository.findByUsername( userDetails.getUsername() );
        User user = userOptional.get();

        survey.setEditable( true );
        user.getSurveys().add( survey );
        users.add( user );
        survey.setUsers( users );
        surveyRepository.save( survey );
        userDetailsService.saveUser( user );
        return new ModelAndView( "redirect:/surveys" );
    }



}
