package com.example.adpotme_api.security;

import com.example.adpotme_api.security.adotante.AdotanteAuthenticationFilter;
import com.example.adpotme_api.security.ongUser.OngUserAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfigurations(CustomAuthenticationProvider customAuthenticationProvider,
                                  PasswordEncoder passwordEncoder) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private OngUserAuthenticationFilter ongUserAuthenticationFilter;

    @Autowired
    private AdotanteAuthenticationFilter adotanteAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions
                .authorizeHttpRequests(authz -> authz
                        // Libera todos os endpoints de documentação e login
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/webjars/**",
                                "/adotantes"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/login/**").permitAll() // Libera o endpoint de login
                        // Restringe o acesso com base nos filtros
                        .requestMatchers("/animais-perdidos/cachorro").hasRole("ADMIN") // Apenas OngUsers ADMIN podem acessar esses endpoints
                        .requestMatchers("/ongusers").hasRole("ADMIN")
                        .anyRequest().authenticated() // Qualquer outro requer autenticação
                )
                .authenticationProvider(customAuthenticationProvider) // Configura o authenticationProvider personalizado
                .addFilterBefore(ongUserAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona o filtro para OngUsers
                .addFilterBefore(adotanteAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Adiciona o filtro para Adotantes

        // ^
        // |
        // O DE CIMA BLOQUEIA TODOS, EXCETO OS QUE ESTÃO COM .permitAll();
        // O DE BAIXO LIBERA TODOS OS ENDPOINTS
        // |
        // V

      /*  http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll() // Permite todas as requisições
                )
                .authenticationProvider(customAuthenticationProvider) // Configura o authenticationProvider personalizado
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona o filtro customizado
                */
        return http.build();
    }

    @Bean
    @Primary
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.authenticationProvider(customAuthenticationProvider);
        return authBuilder.build();
    }
}
