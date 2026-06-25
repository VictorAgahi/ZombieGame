package fr.epita.zombie.user.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/api/health", "/api/*/health")
                    .permitAll()
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
                    .permitAll()
                    .requestMatchers("/api/users/register", "/login", "/register")
                    .permitAll()
                    .requestMatchers("/api/editions/**")
                    .hasRole("ORGANIZER")
                    .requestMatchers("/api/zombies/**")
                    .hasRole("ZOMBIE")
                    .requestMatchers("/api/coureurs/**")
                    .hasRole("RUNNER")
                    .anyRequest()
                    .authenticated())
        .formLogin(Customizer.withDefaults());

    return http.build();
  }
}
