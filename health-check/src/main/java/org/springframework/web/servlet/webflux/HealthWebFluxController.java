package org.springframework.web.servlet.webflux;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthWebFluxController {

  @RequestMapping("/ping")
  public String ping() {
    return "pong";
  }
}

