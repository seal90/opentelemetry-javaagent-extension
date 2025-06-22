package io.github.seal90.opentelemetry.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@SpringBootApplication
public class HelloWorldWebFluxApplication {

  public static void main(String[] args) {
    SpringApplication.run(HelloWorldWebFluxApplication.class, args);
  }

  @RequestMapping("/hello")
  public Mono<String> hello() {
    return Mono.just("hello world");
  }
}
