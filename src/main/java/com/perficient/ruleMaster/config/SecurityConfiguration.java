package com.perficient.ruleMaster.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;


 */
@Configuration
//@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {
/*
    private final String secret = "lengthenoughsecrettotestencryptintallerfinal";

    private final RuleMasterAuthenticationManager ruleMasterAuthenticationManager;

    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(ruleMasterAuthenticationManager);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthorizationManager<RequestAuthorizationContext> access) throws Exception {
        return http
                .cors().and()
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().access(access))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

 */
}