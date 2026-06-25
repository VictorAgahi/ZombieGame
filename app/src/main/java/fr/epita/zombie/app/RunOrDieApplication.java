package fr.epita.zombie.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "fr.epita.zombie")
@EnableJpaRepositories(basePackages = "fr.epita.zombie")
@EntityScan(basePackages = "fr.epita.zombie")
public class RunOrDieApplication {
  public static void main(String[] args) {
    SpringApplication.run(RunOrDieApplication.class, args);
  }
}
