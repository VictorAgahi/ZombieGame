package fr.epita.zombie.edition.application.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/edition")
public class EditionHealthController {

  @GetMapping("/health")
  public String healthCheck() {
    return "Edition Module - System is UP!";
  }
}
