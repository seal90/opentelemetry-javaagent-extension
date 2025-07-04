package org.springframework.web.servlet.web;

import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

public class HealthHandlerMapping implements HandlerMapping, Ordered {

  private final HealthController healthController = new HealthController();

  private final UrlPathHelper urlPathHelper = new UrlPathHelper();

  @Override
  public HandlerExecutionChain getHandler(HttpServletRequest httpServletRequest) throws Exception {
    String lookupPath = urlPathHelper.getLookupPathForRequest(httpServletRequest);
    if("/ping".equals(lookupPath)) {
      httpServletRequest.setAttribute(BEST_MATCHING_PATTERN_ATTRIBUTE, "/ping");
      HandlerMethod handlerMethod = new HandlerMethod(healthController, "ping");
      return new HandlerExecutionChain(handlerMethod);
    }
    return null;
  }

  @Override
  public int getOrder() {
    return 1;
  }

}
