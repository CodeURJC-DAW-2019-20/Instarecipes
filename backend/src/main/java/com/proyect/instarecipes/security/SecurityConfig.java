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
    	
        // Public pages
        //http.antMatcher("/**");
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/index").permitAll(); // ???????????????
        http.authorizeRequests().antMatchers("/ranking").permitAll();
        http.authorizeRequests().antMatchers("/search-page").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/signUp").permitAll();
        http.authorizeRequests().antMatchers("/loginerror").permitAll();
        http.authorizeRequests().antMatchers("/simple-recipe").permitAll();

        // Private pages (all other pages)
        http.authorizeRequests().antMatchers("/profile").hasAnyRole("ROLE_USER");
        http.authorizeRequests().antMatchers("/home").hasAnyRole("ROLE_USER");
        http.authorizeRequests().antMatchers("/admin-profile").hasAnyRole("ROLE_ADMIN");

        // Login form
        http.formLogin().loginPage("/login");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/home");
        http.formLogin().failureUrl("/loginerror");

        // Logout
        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
    	
        // Database authentication provider
        auth.authenticationProvider(authenticationProvider);
    }
}
