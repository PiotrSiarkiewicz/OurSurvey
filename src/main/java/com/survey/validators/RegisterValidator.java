package com.survey.validators;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import com.survey.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;


@Service
public class RegisterValidator
{
    public void checkTokenAndSetMessage( ModelAndView modelAndView, User user )
    {
        if( user == null )
        {
            modelAndView.addObject( "invalidToken", "Oops!  This is an invalid confirmation link." );
        }
        else
        {
            modelAndView.addObject( "confirmationToken", user.getConfirmationToken() );
        }
    }


    public boolean isPasswordNotValid(
                    ModelAndView modelAndView,
                    BindingResult bindingResult,
                    @RequestParam Map requestParams, RedirectAttributes redir )
    {
        Zxcvbn passwordCheck = new Zxcvbn();
        Strength strength = passwordCheck.measure( (String)requestParams.get( "password" ) );

        if( strength.getScore() < 3 )
        {
            bindingResult.reject( "password" );

            redir.addFlashAttribute( "errorMessage", "Your password is too weak.  Choose a stronger one." );

            modelAndView.setViewName( "redirect:confirm?token=" + requestParams.get( "token" ) );
            System.out.println( requestParams.get( "token" ) );
            return true;
        }
        return false;
    }


    public void checkIfUsernameIsUsed(
                    ModelAndView modelAndView,
                    @Valid UserDetails userExists,
                    BindingResult bindingResult )
    {
        if( userExists != null )
        {
            modelAndView.addObject(
                            "error",
                            true );
            modelAndView.setViewName( "register" );
            bindingResult.reject( "email" );
        }
    }
}
