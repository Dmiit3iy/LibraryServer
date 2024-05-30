package com.dmiit3iy.server.security;

import com.dmiit3iy.server.security.jwt.JwtConfigurer;
import com.dmiit3iy.server.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/book/").hasAnyRole("LIBRARIAN", "READER")
                .antMatchers(HttpMethod.POST, "/book").hasRole("LIBRARIAN")
                .antMatchers(HttpMethod.GET, "/book/*").hasAnyRole("LIBRARIAN", "READER")
                .antMatchers(HttpMethod.POST, "/librarian/").hasRole("LIBRARIAN")
                .antMatchers(HttpMethod.GET, "/librarian/").hasRole("LIBRARIAN")
                .antMatchers(HttpMethod.GET, "/librarian/*").hasRole("LIBRARIAN")
                .antMatchers(HttpMethod.POST, "/order/").hasRole("READER")
                .antMatchers(HttpMethod.GET, "/order").hasRole("LIBRARIAN")
                .antMatchers(HttpMethod.GET, "/order/*").hasAnyRole("LIBRARIAN")
                .antMatchers(HttpMethod.GET, "/order/user/*").hasAnyRole("LIBRARIAN", "READER")
                .antMatchers(HttpMethod.PUT, "/order/*/return").hasAnyRole("LIBRARIAN")
                .antMatchers("/reader/").permitAll()
                .antMatchers("/reader/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
