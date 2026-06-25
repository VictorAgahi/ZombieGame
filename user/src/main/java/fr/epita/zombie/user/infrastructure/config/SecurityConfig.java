package fr.epita.zombie.user.infrastructure.config;

import fr.epita.zombie.user.infrastructure.repositories.UserRepository;
import fr.epita.zombie.user.infrastructure.security.UserDetailsConnected;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final UserRepository userRepository;

  public SecurityConfig(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username ->
        userRepository
            .findByEmail(username)
            .map(UserDetailsConnected::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

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
