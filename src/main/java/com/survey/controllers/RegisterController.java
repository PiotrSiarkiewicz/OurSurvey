package com.survey.controllers;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.survey.models.User;
import com.survey.services.EmailService;
import com.survey.services.UserDetailsServiceImpl;
import com.survey.validators.RegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;


@Controller
public class RegisterController
{
    private PasswordEncoder passwordEncoder;
    private UserDetailsServiceImpl userService;
    private EmailService emailService;
    private RegisterValidator registerValidator;

    @Autowired
    public RegisterController(
                    UserDetailsServiceImpl userService,
                    EmailService emailService,
                    RegisterValidator registerValidator ,
                    PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
        this.registerValidator = registerValidator;
        this.userService = userService;
        this.emailService = emailService;
    }


    @RequestMapping( value = "/register", method = RequestMethod.GET )
    public ModelAndView showRegistrationPage( ModelAndView modelAndView, User user )
    {
        modelAndView.addObject( "user", user );
        modelAndView.setViewName( "register" );
        return modelAndView;
    }


    @RequestMapping( value = "/register", method = RequestMethod.POST )
    public ModelAndView processRegistrationForm(
                    ModelAndView modelAndView,
                    @Valid User user,
                    BindingResult bindingResult,
                    HttpServletRequest request )
    {
        checkIfUsernameIsUsed( modelAndView, user, bindingResult );

        if( bindingResult.hasErrors() )
        {
            modelAndView.setViewName( "register" );
        }
        else
        {
            saveDisabledUser( user );
            SimpleMailMessage registrationEmail = emailService.createEmailContent( user, request );
            emailService.sendEmail( registrationEmail );

            modelAndView.addObject(
                            "confirmationMessage", "A confirmation e-mail has been sent to " + user.getEmail() );
            modelAndView.setViewName( "register" );
        }

        return modelAndView;
    }


    @RequestMapping( value = "/confirm", method = RequestMethod.GET )
    public ModelAndView showConfirmationPage( ModelAndView modelAndView, @RequestParam( "token" ) String token )
    {
        User user = userService.findByConfirmationToken( token );
        registerValidator.checkToken( modelAndView, user );

        modelAndView.setViewName( "confirm" );
        return modelAndView;
    }


    @RequestMapping( value = "/confirm", method = RequestMethod.POST )
    public ModelAndView processConfirmationForm(
                    ModelAndView modelAndView,
                    BindingResult bindingResult,
                    @RequestParam Map requestParams,
                    RedirectAttributes redir )
    {
        modelAndView.setViewName( "confirm" );

        if( registerValidator.isPasswordNotValid( modelAndView, bindingResult, requestParams, redir ) )
            return modelAndView;
        enableUserAndSetNewPassword( requestParams );

        modelAndView.addObject( "successMessage", "Your password has been set!" );
        return modelAndView;
    }


    private void enableUserAndSetNewPassword( @RequestParam Map requestParams )
    {
        User user = userService.findByConfirmationToken( (String)requestParams.get( "token" ) );
        user.setPassword( passwordEncoder.encode( (CharSequence)requestParams.get( "password" ) ) );
        user.setEnabled( true );
        userService.saveUser( user );
    }


    private void saveDisabledUser( @Valid User user )
    {
        user.setEnabled( false );
        user.setConfirmationToken( UUID.randomUUID().toString() );
        userService.saveUser( user );
    }


    private void checkIfUsernameIsUsed( ModelAndView modelAndView, @Valid User user, BindingResult bindingResult )
    {
        UserDetails userExists = userService.loadUserByUsername( user.getUsername() );
        registerValidator.checkIfUsernameIsUsed( modelAndView, userExists, bindingResult );
    }
}
