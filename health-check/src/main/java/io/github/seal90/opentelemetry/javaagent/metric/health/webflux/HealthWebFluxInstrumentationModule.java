package io.github.seal90.opentelemetry.javaagent.metric.health.webflux;

import com.google.auto.service.AutoService;
import io.opentelemetry.javaagent.extension.instrumentation.HelperResourceBuilder;
import io.opentelemetry.javaagent.extension.instrumentation.InstrumentationModule;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.instrumentation.internal.ExperimentalInstrumentationModule;
import net.bytebuddy.matcher.ElementMatcher;

import java.util.Collections;
import java.util.List;

import static io.opentelemetry.javaagent.extension.matcher.AgentElementMatchers.hasClassesNamed;

@AutoService(InstrumentationModule.class)
public class HealthWebFluxInstrumentationModule extends InstrumentationModule implements ExperimentalInstrumentationModule {

  public HealthWebFluxInstrumentationModule() {
    super("spring-webflux", "spring-webflux-server");
  }

  @Override
  public ElementMatcher.Junction<ClassLoader> classLoaderMatcher() {
    return hasClassesNamed("org.springframework.web.reactive.DispatcherHandler");
  }

  @Override
  public List<TypeInstrumentation> typeInstrumentations() {
    return Collections.singletonList(new HealthWebFluxTypeInstrumentation());
  }

  @Override
  public void registerHelperResources(HelperResourceBuilder helperResourceBuilder) {
    if (!isIndyModule()) {
      helperResourceBuilder.registerForAllClassLoaders(
          "org/springframework/web/servlet/webflux/HealthWebFluxHandlerMapping.class");
      helperResourceBuilder.registerForAllClassLoaders(
          "org/springframework/web/servlet/webflux/HealthWebFluxController.class");
    }
  }

  public boolean isHelperClass(String className) {
    return className.startsWith("org.springframework.web.servlet.webflux.HealthWebFluxHandlerMapping")
        || className.startsWith("org.springframework.web.servlet.webflux.HealthWebFluxController");
  }

  @Override
  public String getModuleGroup() {
    return "netty";
  }

  @Override
  public int order() {
    return 100;
  }
}
