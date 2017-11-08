package com.survey.controllers;

import org.springframework.boot.SpringBootConfiguration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController
{
    @RequestMapping( "/login" )
    public String login()
    {
        return "login";
    }


    @RequestMapping( value = { "/home", "/" } )
    public String home()
    {
        return "home";
    }
}



