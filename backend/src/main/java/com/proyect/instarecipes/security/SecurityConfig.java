package com.proyect.instarecipes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public UserAuthProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        // Private pages (all other pages)
        http.authorizeRequests().antMatchers("/profile").hasAnyRole("USER");
        // http.authorizeRequests().antMatchers("/index").hasAnyRole("USER","ADMIN");

        // Public pages
        //http.antMatcher("/**");
        // Other URLs can be accessed without authentication
		http.authorizeRequests().anyRequest().permitAll();

        // Login form
        http.formLogin().loginPage("/login");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/index");
        http.formLogin().failureUrl("/login");

        // Logout
        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/");

        //This is for ore security to the posst
        // http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
    	
        // Database authentication provider
        auth.authenticationProvider(authenticationProvider);
    }
}
