package com.springboot.miniecommercewebapp.config;

import com.springboot.miniecommercewebapp.filter.JwtFilter;
import com.springboot.miniecommercewebapp.jwtUtils.JwtAccessDenied;
import com.springboot.miniecommercewebapp.jwtUtils.JwtAuthenticationEntryPoint;
import com.springboot.miniecommercewebapp.repositories.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = false, securedEnabled = false, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    JwtAccessDenied jwtAccessDenied;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private JwtFilter filter;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/register/**").permitAll()
                //USER
                .antMatchers(GET,"/users/**").hasRole("ADMIN")
                .antMatchers(POST,"/users/**").hasRole("ADMIN")
                .antMatchers(PUT,"/users/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(DELETE,"/users/**").hasAnyRole("ADMIN", "USER")
                // PRODUCT
                .antMatchers(GET,"/products/**").permitAll()
                .antMatchers(POST,"/products/**").hasRole("ADMIN")
                .antMatchers(PUT,"/products/**").hasRole("ADMIN")
                .antMatchers(DELETE,"/products/**").hasRole("ADMIN")
                // CART
                .antMatchers(GET, "/cart/**").hasRole("USER")
                .antMatchers(POST, "/cart/**").hasRole("USER")
                .antMatchers(PUT, "/cart/**").hasRole("USER")
                .antMatchers(DELETE, "/cart/**").hasRole("USER")
                // ORDER
                .antMatchers(GET, "/order/**").hasAnyRole("ADMIN","USER")
                .antMatchers(POST, "/order/**").hasAnyRole("ADMIN","USER")
                .antMatchers(PUT, "/order/**").hasAnyRole("ADMIN","USER")
                .antMatchers(DELETE, "/order/**").hasAnyRole("ADMIN","USER")
                // ORDER DETAIL
                .antMatchers(GET, "/orderItem/**").hasAnyRole("ADMIN","USER")
                .antMatchers(POST, "/orderItem/**").hasAnyRole("ADMIN","USER")
                .antMatchers(PUT, "/orderItem/**").hasAnyRole("ADMIN","USER")
                .antMatchers(DELETE, "/orderItem/**").hasAnyRole("ADMIN","USER")
                // CATEGORY
                .antMatchers(GET, "/category/**").permitAll()
                .antMatchers(POST, "/category/**").hasRole("ADMIN")
                .antMatchers(PUT, "/category/**").hasRole("ADMIN")
                .antMatchers(DELETE, "/category/**").hasRole("ADMIN")
                // RATE
                .antMatchers(GET, "/rate/**").hasRole("USER")
                .antMatchers(POST, "/rate/**").hasRole("USER")
                .antMatchers(PUT, "/rate/**").hasRole("USER")
                .antMatchers(DELETE, "/rate/**").hasRole("USER")
                .anyRequest()
                .authenticated();
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(jwtAccessDenied);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedOrigin("*");
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}