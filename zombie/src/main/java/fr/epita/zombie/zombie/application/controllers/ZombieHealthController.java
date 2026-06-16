package fr.epita.zombie.zombie.application.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/zombie")
public class ZombieHealthController {

  @GetMapping("/health")
  public String healthCheck() {
    return "Zombie Module - System is UP!";
  }
}
