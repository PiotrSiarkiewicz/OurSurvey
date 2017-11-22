package com.survey.services;

import com.survey.models.Survey;
import com.survey.models.User;
import com.survey.models.UserDetailsImpl;
import com.survey.repository.SurveyRepository;
import com.survey.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Service
public class SurveyService
{

    private SurveyRepository surveyRepository;
    private UserRepository userRepository;


    @Autowired
    public SurveyService( SurveyRepository surveyRepository, UserRepository userRepository )
    {

        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
    }


    public Survey findSurveyById( @PathVariable Long surveyId, Model model )
    {
        List<Survey> surveys = getSurveysFromLoggedUser();
        for( Iterator<Survey> iterator = surveys.iterator(); iterator.hasNext(); )
        {
            Survey survey = iterator.next();
            if( survey.getId().equals( surveyId ) )
            {
                model.addAttribute( "survey", survey );
                return survey;
            }
        }
        return null;
    }


    public void removeSurveyFromSurveyList( @PathVariable Long surveyId )
    {
        List<Survey> surveys = getSurveysFromLoggedUser();
        surveys.removeIf( survey -> survey.getId().equals( surveyId ) );
    }


    public List<Survey> getSurveysFromLoggedUser()
    {
        UserDetailsImpl userDetails =
                        (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername( userDetails.getUsername() );
        return new ArrayList<>( user.get().getSurveys() );
    }


    public User getLoggedUser()
    {
        List<User> users = new ArrayList<>();
        UserDetailsImpl userDetails =
                        (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOptional = userRepository.findByUsername( userDetails.getUsername() );
        return userOptional.get();
    }


    public void prepareSurveyAndUserToSave( Survey survey, User user )
    {
        List<User> users = new ArrayList<>();
        user.getSurveys().add( survey );
        users.add( user );
        survey.setUsers( users );
    }
}
