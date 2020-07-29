package com.alexcode.photoapp.api.gateway.PhotoAppZuulApiGateway.security;

import com.alexcode.photoapp.api.gateway.PhotoAppZuulApiGateway.jwt.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Environment env;
    private final JwtConfig jwtConfig;

    @Autowired
    public ApplicationSecurityConfig(Environment env, JwtConfig jwtConfig) {
        this.env = env;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers(env.getProperty("api.h2console.path")).permitAll()
                .antMatchers(env.getProperty("api.zuul.actuator.path")).permitAll()
                .antMatchers(env.getProperty("api.users.actuator.path")).permitAll()
                .antMatchers(HttpMethod.POST, env.getProperty("api.registration.path")).permitAll()
                .antMatchers(HttpMethod.POST, env.getProperty("api.login.path")).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new AuthorizationFilter(authenticationManager(), jwtConfig))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
