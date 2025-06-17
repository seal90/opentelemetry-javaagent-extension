package org.springframework.web.servlet.v3_1;

import org.springframework.core.Ordered;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;


public class HealthCheckHandlerMappingFilter implements Filter, Ordered {
  private static final Logger logger =
      Logger.getLogger(HealthCheckHandlerMappingFilter.class.getName());

  @Override
  public void init(FilterConfig filterConfig) {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
      filterChain.doFilter(request, response);
      return;
    }

    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String requestURI = httpServletRequest.getRequestURI();
    if("/ping".equals(requestURI)) {
      HttpServletResponse httpServletResponse = (HttpServletResponse) response;
      httpServletResponse.setStatus(200);
      try(Writer writer = httpServletResponse.getWriter()) {
        writer.write("pong");
      }
    } else {
      filterChain.doFilter(request, response);
    }
  }

  @Override
  public void destroy() {}

  @Override
  public int getOrder() {
    // Run after all HIGHEST_PRECEDENCE items
    return Ordered.HIGHEST_PRECEDENCE + 2;
  }
}
