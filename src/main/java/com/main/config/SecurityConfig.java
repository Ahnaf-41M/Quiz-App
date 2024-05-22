package com.main.config;

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

        return http.csrf(csrf -> csrf.disable()).authorizeRequests()
                .antMatchers("/", "/home", "/registerUser").permitAll()
                .antMatchers("/startQuiz", "/submitQuiz", "/scoreList", "/myScore").authenticated()
                .anyRequest().permitAll().and().formLogin(form -> form.loginPage("/home")
                        .loginProcessingUrl("/login").defaultSuccessUrl("/startQuiz").permitAll())
                .build();
        // http.csrf((csrf) -> csrf.disable())
        // .authorizeRequests((authz) -> authz.requestMatchers("/").permitAll()
        // .requestMatchers("/user/**", "/profile").authenticated().anyRequest()
        // .permitAll())
        // .formLogin(form -> form.loginPage("/signin").loginProcessingUrl("/login")
        // .defaultSuccessUrl("/profile").permitAll());

        // return http.build();
    }
}
