package antifraud.configuration;

import antifraud.handler.AuthenticationEntryPointHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final AuthenticationEntryPointHandler authenticationEntryPointHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPointHandler) // Handles auth error
                .and()
                .csrf().disable()
                .headers().frameOptions().disable() // for Postman, the H2 console
                .and()
                .authorizeRequests(c -> c
                        .mvcMatchers(HttpMethod.POST, "/api/auth/user").permitAll()
                        .mvcMatchers("/actuator/shutdown").permitAll()
//                        .anyRequest().permitAll()
                        .anyRequest().authenticated()
                       )
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // no session

        return http.build();

    }

}
