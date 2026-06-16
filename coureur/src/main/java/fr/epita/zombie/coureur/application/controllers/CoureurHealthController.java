package fr.epita.zombie.coureur.application.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coureur")
public class CoureurHealthController {

  @GetMapping("/health")
  public String healthCheck() {
    return "Coureur Module - System is UP!";
  }
}
