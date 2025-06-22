# Build 
[javaagent-extension](javaagent-extension) is javaagent extension project.
# Config start env args 
Pay attention to modify AGENT_PATH PROJECT_PATH
```shell
JAVA_TOOL_OPTIONS="-javaagent:AGENT_PATH/opentelemetry-javaagent-2.16.0.jar";OTEL_JAVAAGENT_EXTENSIONS=PROJECT_PATH/opentelemetry-javaagent-extension/health-check/build/libs/health-check-1.0.jar;OTEL_TRACES_EXPORTER=none;OTEL_METRICS_EXPORTER=prometheus,logging-otlp;OTEL_LOGS_EXPORTER=none
```
# Start web
[HelloWorldApplication.java](examples/spring-web/src/main/java/io/github/seal90/opentelemetry/web/HelloWorldApplication.java)

# Start webflux
[HelloWorldWebFluxApplication.java](examples/spring-webflux/src/main/java/io/github/seal90/opentelemetry/webflux/HelloWorldWebFluxApplication.java)

# Problem

## AnnotationConfigReactiveWebServerApplicationContext failed for advice
[HealthWebFluxTypeInstrumentation.java](health-check/src/main/java/io/github/seal90/opentelemetry/javaagent/metric/health/webflux/HealthWebFluxTypeInstrumentation.java)

````java
  @Override
  public ElementMatcher<TypeDescription> typeMatcher() {
//    return extendsClass(named("org.springframework.boot.web.reactive.context.GenericReactiveWebApplicationContext"));
//    return named("org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext");
    return extendsClass(named("org.springframework.context.support.AbstractApplicationContext"));
  }
````