package com.survey.config;

import com.survey.repository.UserRepository;
import com.survey.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableGlobalMethodSecurity( prePostEnabled = true )
@EnableJpaRepositories( basePackageClasses = UserRepository.class )
@Configuration
public class SecurityConfiguration
                extends WebSecurityConfigurerAdapter
{
    private UserDetailsServiceImpl userDetailsService;


    @Autowired
    public SecurityConfiguration( UserDetailsServiceImpl userDetailsService )
    {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }


    @Override
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception
    {
        auth.userDetailsService( userDetailsService ).passwordEncoder(
                        passwordEncoder() );
    }


    @Override
    protected void configure( HttpSecurity http ) throws Exception
    {
        http.authorizeRequests()
                        .antMatchers( "/register", "/home", "/login", "/", "/confirm" ).permitAll()
                        .anyRequest().authenticated()
                        .and()
                        .formLogin()
                        .loginPage( "/login" )
                        .defaultSuccessUrl( "/surveys" );
    }
}
