package io.github.seal90.opentelemetry.javaagent.metric.health.web;

import com.google.auto.service.AutoService;
import io.opentelemetry.javaagent.extension.instrumentation.HelperResourceBuilder;
import io.opentelemetry.javaagent.extension.instrumentation.InstrumentationModule;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import net.bytebuddy.matcher.ElementMatcher;

import java.util.Collections;
import java.util.List;

import static io.opentelemetry.javaagent.extension.matcher.AgentElementMatchers.hasClassesNamed;

@AutoService(InstrumentationModule.class)
public class HealthWebInstrumentationModule extends InstrumentationModule {

  public HealthWebInstrumentationModule() {
    super("spring-web", "spring-webmvc");
  }

  @Override
  public ElementMatcher.Junction<ClassLoader> classLoaderMatcher() {
    return hasClassesNamed("org.springframework.web.servlet.HandlerMapping",
        "org.springframework.web.servlet.DispatcherServlet");
  }

  @Override
  public List<TypeInstrumentation> typeInstrumentations() {
    return Collections.singletonList(new HealthWebTypeInstrumentation());
  }

  @Override
  public void registerHelperResources(HelperResourceBuilder helperResourceBuilder) {
    if (!isIndyModule()) {
      helperResourceBuilder.registerForAllClassLoaders(
          "org/springframework/web/servlet/web/HealthHandlerMapping.class");
      helperResourceBuilder.registerForAllClassLoaders(
          "org/springframework/web/servlet/web/HealthController.class");
    }
  }

  public boolean isHelperClass(String className) {
    return className.startsWith("org.springframework.web.servlet.web.HealthHandlerMapping")
        || className.startsWith("org.springframework.web.servlet.web.HealthController");
  }
}
