package com.main.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.main.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    CustomAuthSucessHandler customAuthSucessHandler;

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService getDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(getDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return daoAuthenticationProvider;

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // return http.csrf(csrf -> csrf.disable()).authorizeRequests()
        // .antMatchers("/", "/home", "/registerUser").permitAll()
        // .antMatchers("/startQuiz", "/submitQuiz", "/topScores", "/myResult", "/dashboard")
        // .authenticated().anyRequest().permitAll().and()
        // .formLogin(form -> form.loginPage("/home").loginProcessingUrl("/login")
        // .defaultSuccessUrl("/dashboard").permitAll())
        // .build();

        return http.csrf(csrf -> csrf.disable()).authorizeRequests().antMatchers("/user/**")
                .hasRole("USER").antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/**")
                .permitAll().and()
                .formLogin(form -> form.loginPage("/home").loginProcessingUrl("/login")
                        .successHandler(customAuthSucessHandler).permitAll())
                .build();
    }
}
