package ru.echominds.infohub.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.echominds.infohub.security.jwt.JWTValidationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthorizationSuccessHandlerImpl authorizationSuccessHandler,
            JWTValidationFilter jwtValidationFilter
    ) throws Exception {
        return http
                .csrf((AbstractHttpConfigurer::disable))
                .oauth2Login(oath2LoginConfig -> oath2LoginConfig
                        .successHandler(authorizationSuccessHandler)
                        .defaultSuccessUrl("/api/user")
                        .failureUrl("/login?error=true")
                )
                .authorizeHttpRequests(httpAuth -> httpAuth
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/user").authenticated()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}