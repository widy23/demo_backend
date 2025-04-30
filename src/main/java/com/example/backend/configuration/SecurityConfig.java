package com.example.backend.configuration;

import com.example.backend.jwt.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final  AuthenticationProvider authProvider;

    private final JwtAuthenticationFilter jwtFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint; // Inyecta tu EntryPoint
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity secured) throws Exception {
        return secured.csrf (AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRequest->
                        authRequest
                        .requestMatchers(
                                "/auth/**",
                               "actuator/circuitbreakers**").permitAll()
                                .requestMatchers("/actuator/**").permitAll()
                                .requestMatchers("/demobackend-production-3308.up.railway.app**").permitAll()
                                .requestMatchers("/api/v1/**").authenticated()
                        .anyRequest()
                                .authenticated()
                )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(Customizer.withDefaults()) // Configura el soporte JWT (ajústalo si es necesario)
//                        // Aquí es donde le dices que use tu manejador para fallos de autenticación
//                        .authenticationEntryPoint(customAuthenticationEntryPoint)
//                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterAt(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/auth/logout")
                                .addLogoutHandler((request, response, authentication) -> {
                                    final var authHear =request.getHeader(HttpHeaders.AUTHORIZATION);
                                    logout(authHear);
                                })
                                .logoutSuccessHandler((request, response, authentication) -> {
                                    SecurityContextHolder.clearContext();
                                })
                )
                .build();

    }

    private void logout(String token) {
//        if (token ==null || !token.startsWith("Bearer ")){
//            throw new IllegalArgumentException("Invalid token ");
//        }
//        final String jwtToken = token.substring(7);
//        final Token foundToken = repositoryToken.findByToken(jwtToken)
//                .orElseThrow(()->new IllegalArgumentException("invalid token"));
//        foundToken.setExpired(true);
//        foundToken.setRevoked(true);
//        repositoryToken.save(foundToken);
    }
    // Aca se puede manejar el Logout
}
