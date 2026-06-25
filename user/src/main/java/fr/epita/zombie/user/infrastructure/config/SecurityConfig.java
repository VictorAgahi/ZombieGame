package fr.epita.zombie.user.infrastructure.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                    .requestMatchers(
                        "/api/users/register", "/api/users/login", "/login", "/register")
                    .permitAll()
                    .requestMatchers("/api/editions/**")
                    .hasRole("ORGANIZER")
                    .requestMatchers("/api/zombies/**")
                    .hasRole("ZOMBIE")
                    .requestMatchers("/api/coureurs/**")
                    .hasRole("RUNNER")
                    .anyRequest()
                    .authenticated())
        .formLogin(
            form ->
                form.loginProcessingUrl("/api/users/login")
                    .successHandler(
                        (request, response, authentication) -> {
                          response.setStatus(HttpServletResponse.SC_OK);
                          response.setContentType("application/json");
                          response.getWriter().write("{\"message\":\"Login successful\"}");
                        })
                    .failureHandler(
                        (request, response, exception) -> {
                          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                          response.setContentType("application/json");
                          response.getWriter().write("{\"error\":\"Invalid email or password\"}");
                        }));

    return http.build();
  }
}
