package com.proyect.instarecipes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(1)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    public UserAuthProvider authenticationProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.antMatcher("/api/**");
		
		// http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/login").authenticated();
		
		// URLs that need authentication to access to it
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/index/").hasRole("USER");
		// http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/books/**").hasRole("ADMIN");
		// http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/books/**").hasRole("ADMIN");
		// http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN");		
		
		// Other URLs can be accessed without authentication
		http.authorizeRequests().anyRequest().permitAll();

		//Login form
		http.formLogin().loginPage("/api/login");
		http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
		http.formLogin().failureUrl("/api/login");
		http.logout().logoutUrl("/api/logout");
		
		// Disable CSRF protection (it is difficult to implement in REST APIs)
		http.csrf().disable();

		// Use Http Basic Authentication
		http.httpBasic();

		// Do not redirect when logout
		http.logout().logoutSuccessHandler((rq, rs, a) -> {	});
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// Database authentication provider
		auth.authenticationProvider(authenticationProvider);
	}
}