package org.springframework.web.servlet.web;

import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

public class HealthHandlerMapping implements HandlerMapping, Ordered {

  private final HealthController healthController = new HealthController();

  @Override
  public HandlerExecutionChain getHandler(HttpServletRequest httpServletRequest) throws Exception {
    String requestURI = httpServletRequest.getRequestURI();
    if("/ping".equals(requestURI)) {
      HandlerMethod handlerMethod = new HandlerMethod(healthController, "ping");
      return new HandlerExecutionChain(handlerMethod);
    }
    return null;
  }

  @Override
  public int getOrder() {
    return 1;
  }

  public HealthController getHealthController() {
    return healthController;
  }
}
