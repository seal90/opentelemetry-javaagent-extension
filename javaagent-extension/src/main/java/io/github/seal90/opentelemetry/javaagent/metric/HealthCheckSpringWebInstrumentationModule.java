/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.github.seal90.opentelemetry.javaagent.metric;

import com.google.auto.service.AutoService;
import io.opentelemetry.javaagent.extension.instrumentation.HelperResourceBuilder;
import io.opentelemetry.javaagent.extension.instrumentation.InstrumentationModule;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.instrumentation.internal.ExperimentalInstrumentationModule;
import net.bytebuddy.matcher.ElementMatcher;

import java.util.List;

import static io.opentelemetry.javaagent.extension.matcher.AgentElementMatchers.hasClassesNamed;
import static java.util.Collections.singletonList;
import static net.bytebuddy.matcher.ElementMatchers.not;

@AutoService(InstrumentationModule.class)
public class HealthCheckSpringWebInstrumentationModule extends InstrumentationModule
    implements ExperimentalInstrumentationModule {
  public HealthCheckSpringWebInstrumentationModule() {
    super("spring-web", "spring-web-3.1");
  }

  @Override
  public ElementMatcher.Junction<ClassLoader> classLoaderMatcher() {
    // class added in 3.1
    return hasClassesNamed("org.springframework.web.method.HandlerMethod")
        // class added in 6.0
        .and(not(hasClassesNamed("org.springframework.web.ErrorResponse")));
  }

  @Override
  public void registerHelperResources(HelperResourceBuilder helperResourceBuilder) {
    if (!isIndyModule()) {
      System.out.println("registerHelperResources");
      // make the filter class file loadable by ClassPathResource - in some cases (e.g.
      // spring-guice,
      // see https://github.com/open-telemetry/opentelemetry-java-instrumentation/issues/7428)
      // Spring
      // might want to read the class file metadata; this line will make the filter class file
      // visible
      // to the bean class loader
      helperResourceBuilder.register(
          "org/springframework/web/servlet/v3_1/HealthCheckHandlerMappingFilter.class");
      try {
        Object val = helperResourceBuilder.getClass().getMethod("getResources").invoke(helperResourceBuilder);
        System.out.println("registerHelperResources 22" + val);
      } catch (Exception e) {
      }

      System.out.println("registerHelperResources 2" + helperResourceBuilder);
    }
  }

//  public List<String> getAdditionalHelperClassNames() {
//    return Collections.singletonList(HealthCheckHandlerMappingFilter.class.getName());
//  }

  @Override
  public String getModuleGroup() {
    return "servlet";
  }

  @Override
  public List<TypeInstrumentation> typeInstrumentations() {
    return singletonList(new HealthCheckWebApplicationContextInstrumentation());
  }

}
