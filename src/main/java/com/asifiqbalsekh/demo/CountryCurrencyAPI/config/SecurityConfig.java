package com.asifiqbalsekh.demo.CountryCurrencyAPI.config;

import com.asifiqbalsekh.demo.CountryCurrencyAPI.dto.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain mySecurityFilterChain(HttpSecurity http) throws Exception {



        //we can chain all the setting and directly return it like below


        return http

                .authorizeHttpRequests
                    (request-> {

                        request.requestMatchers(
                                        "/user/roles","/user/deactivate/**"
                                )
                                .hasAuthority(Roles.ADMIN.name());


                        request.requestMatchers(HttpMethod.GET,

                                        "/user"
                                )
                                .hasAuthority(Roles.ADMIN.name());

                        request.requestMatchers(HttpMethod.DELETE,

                                        "/user/**"
                                )
                                .hasAuthority(Roles.ADMIN.name());

                        request.requestMatchers(HttpMethod.DELETE,

                                        "/country-currency**"
                                )
                                .hasAuthority(Roles.MANAGER.name());

                        request.requestMatchers(HttpMethod.GET,
                                        "/country-currency",
                                        "/post-office/testing",
                                "/actuator/**"
                                )
                                .permitAll();
                        request.requestMatchers(HttpMethod.POST,
                                        "/user/login","/user"
                                )
                                .permitAll();

                        request.anyRequest().authenticated();

                    })
                .csrf(AbstractHttpConfigurer::disable)
                .userDetailsService(userDetailsService)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

