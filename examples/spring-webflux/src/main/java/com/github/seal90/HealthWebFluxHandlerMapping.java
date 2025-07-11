package com.github.seal90;

import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class HealthWebFluxHandlerMapping implements HandlerMapping, Ordered {

  private HealthWebFluxController healthWebFluxController = new HealthWebFluxController();

  @Override
  public Mono<Object> getHandler(ServerWebExchange exchange) {
    ServerHttpRequest request = exchange.getRequest();
    ServerHttpResponse response = exchange.getResponse();
    String requestURI = request.getURI().getPath();
    if("/ping".equals(requestURI)) {
      try {
        HandlerMethod handlerMethod = new HandlerMethod(healthWebFluxController, "ping");
        return Mono.just(handlerMethod);
      } catch (NoSuchMethodException e) {
        throw new RuntimeException(e);
      }
    }
    return Mono.empty();
  }

  public HealthWebFluxController healthWebFluxController() {
    return healthWebFluxController;
  }

  @Override
  public int getOrder() {
    return 1;
  }

}
