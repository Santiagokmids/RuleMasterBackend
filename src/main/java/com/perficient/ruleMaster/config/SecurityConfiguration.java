package com.perficient.ruleMaster.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.access.intercept.RequestMatcherDelegatingAuthorizationManager;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    private final String secret = "lengthenoughsecrettotestencryptintallerfinal";

    private final RuleMasterAuthenticationManager ruleMasterAuthenticationManager;

    @Bean
    public AuthenticationManager authenticationManager() {
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

    @Bean
    public JwtDecoder jwtDecoder(){
        byte[] bytes = secret.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(bytes, 0, bytes.length, "RSA");

        return NimbusJwtDecoder.withSecretKey(keySpec).macAlgorithm(MacAlgorithm.HS256).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(){
        return new NimbusJwtEncoder(new ImmutableSecret<>(secret.getBytes()));
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> requestAuthorizationContextAuthorizationManager
            (HandlerMappingIntrospector introspector){

        RequestMatcher permitAll = new AndRequestMatcher(new MvcRequestMatcher(introspector, "/token"));

        RequestMatcherDelegatingAuthorizationManager.Builder managerBuilder =
            RequestMatcherDelegatingAuthorizationManager.builder()
                    .add(permitAll, (context, other) -> new AuthorizationDecision(true));

        managerBuilder.add(new MvcRequestMatcher(introspector, "/users/**"), AuthorityAuthorizationManager.hasAnyAuthority("SCOPE_Administrador","SCOPE_Gestor_de_columnas", "SCOPE_Gestor_de_registros", "SCOPE_Gestor_de_reglas"));

        managerBuilder.add(new MvcRequestMatcher(introspector, "/table/**"), AuthorityAuthorizationManager.hasAnyAuthority("SCOPE_Gestor_de_columnas", "SCOPE_Gestor_de_registros", "SCOPE_Gestor_de_reglas"));

        managerBuilder.add(new MvcRequestMatcher(introspector, "/rules/**"), AuthorityAuthorizationManager.hasAnyAuthority("SCOPE_Gestor_de_registros", "SCOPE_Gestor_de_reglas"));

        MvcRequestMatcher  tempMvcRequestMatcher = new MvcRequestMatcher(introspector, "/users");
        managerBuilder.add(tempMvcRequestMatcher, AuthorityAuthorizationManager.hasAnyAuthority("SCOPE_Administrador"));

        MvcRequestMatcher  tempMvcRequestMatcher2 = new MvcRequestMatcher(introspector, "/rules");
        managerBuilder.add(tempMvcRequestMatcher2, AuthorityAuthorizationManager.hasAnyAuthority("SCOPE_Gestor_de_reglas"));

        AuthorizationManager<HttpServletRequest> manager = managerBuilder.build();
        return ((authentication, object) -> manager.check(authentication, object.getRequest()));
    }
}