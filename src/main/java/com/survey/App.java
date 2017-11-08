package com.survey;

import groovy.lang.Grab;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
@SpringBootConfiguration
public class App
                extends SpringBootServletInitializer
{

    @Override
    protected SpringApplicationBuilder configure( SpringApplicationBuilder application )
    {
        return application.sources( App.class );
    }


    public static void main( String[] args )
    {
        ApplicationContext ctx = SpringApplication.run( App.class, args );
    }
}
