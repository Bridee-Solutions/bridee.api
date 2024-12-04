package com.bridee.api.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtFilter;
    private final CustomJwtConverter jwtConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(httpRequest -> {
                        httpRequest
                                .requestMatchers("/authentication").permitAll()
                                .requestMatchers(HttpMethod.GET, "/usuarios/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/casais",
                                        "/assessores",
                                        "/casais/externo",
                                        "/assessores/externo",
                                        "/assessores/validate-fields").permitAll()
                                .requestMatchers("/v3/api-docs/**",
                                        "/swagger-ui/**").permitAll()
                                .requestMatchers("/casais/**", "/convidados/**",
                                        "/convites/**", "/dashboards/**", "/itens-orcamento/**",
                                        "/mesas/**", "/orcamentos/**", "/orcamento-fornecedor/**").hasRole("CASAL")
                                .requestMatchers("/assessores/**").hasRole("ASSESSOR")
                                .requestMatchers("/tarefas/**").hasAnyRole("CASAL", "ASSESSOR")
                                .anyRequest().permitAll();
                })
                .csrf(CsrfConfigurer::disable)
                .cors(Customizer.withDefaults())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2ResourceServer(resourceServer -> {
                    resourceServer.jwt(jwt -> {
                        jwt.jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                                .jwtAuthenticationConverter(jwtConverter);
                    });
                })
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers")  );
        config.setAllowedMethods(Arrays.asList("DELETE", "GET", "PUT", "POST", "OPTIONS"));
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
