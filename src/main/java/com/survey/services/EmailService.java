package com.survey.services;

import com.survey.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Service( "emailService" )
public class EmailService
{

    private JavaMailSender mailSender;


    @Autowired
    public EmailService( JavaMailSender mailSender )
    {
        this.mailSender = mailSender;
    }


    @Async
    public void sendEmail( SimpleMailMessage email )
    {
        mailSender.send( email );
    }

    public SimpleMailMessage createEmailContent( @Valid User user, HttpServletRequest request )
    {
        String appUrl = request.getScheme() + "://" + request.getServerName() + ":8080";
        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo( user.getEmail() );
        registrationEmail.setSubject( "Registration Confirmation" );
        registrationEmail.setText( "To confirm your e-mail address, please click the link below:\n"
                        + appUrl + "/confirm?token=" + user.getConfirmationToken() );
        registrationEmail.setFrom( "noreply@domain.com" );
        return registrationEmail;
    }
}
