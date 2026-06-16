package fr.epita.zombie.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "fr.epita.zombie")
public class RunOrDieApplication {
  public static void main(String[] args) {
    SpringApplication.run(RunOrDieApplication.class, args);
  }
}
