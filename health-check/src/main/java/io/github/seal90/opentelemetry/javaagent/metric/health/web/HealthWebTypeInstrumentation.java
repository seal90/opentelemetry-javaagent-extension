package io.github.seal90.opentelemetry.javaagent.metric.health.web;

import io.opentelemetry.javaagent.bootstrap.InstrumentationProxyHelper;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.instrumentation.TypeTransformer;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.web.servlet.web.HealthController;
import org.springframework.web.servlet.web.HealthHandlerMapping;

import static io.opentelemetry.javaagent.extension.matcher.AgentElementMatchers.extendsClass;
import static io.opentelemetry.javaagent.extension.matcher.AgentElementMatchers.hasClassesNamed;
import static io.opentelemetry.javaagent.extension.matcher.AgentElementMatchers.implementsInterface;
import static net.bytebuddy.matcher.ElementMatchers.isMethod;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArgument;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

public class HealthWebTypeInstrumentation implements TypeInstrumentation {

  @Override
  public ElementMatcher<ClassLoader> classLoaderOptimization() {
    return hasClassesNamed(
        "org.springframework.context.support.AbstractApplicationContext",
        "org.springframework.web.context.WebApplicationContext");
  }

  @Override
  public ElementMatcher<TypeDescription> typeMatcher() {
    return extendsClass(named("org.springframework.context.support.AbstractApplicationContext"))
        .and(implementsInterface(named("org.springframework.web.context.WebApplicationContext")));
  }

  @Override
  public void transform(TypeTransformer transformer) {
    transformer.applyAdviceToMethod(
        isMethod()
            .and(named("postProcessBeanFactory"))
            .and(takesArgument(0,
                named("org.springframework.beans.factory.config.ConfigurableListableBeanFactory"))),
        HealthWebTypeInstrumentation.class.getName() + "$FilterInjectingAdvice");
  }


  @SuppressWarnings("unused")
  public static class FilterInjectingAdvice {

    @Advice.OnMethodEnter(suppress = Throwable.class)
    public static void onEnter(@Advice.Argument(0) ConfigurableListableBeanFactory beanFactory) {
      if (beanFactory instanceof BeanDefinitionRegistry
          && !beanFactory.containsBean("healthHandlerMapping")) {
        // Explicitly loading classes allows to catch any class-loading issue or deal with cases
        // where the class is not visible.
        try {
          // Firstly check whether DispatcherServlet is present. We need to load an instrumented
          // class from spring-webmvc to trigger injection that makes
          // HealthHandlerMapping available.
          Class<?> dispatcherServletClass =
              beanFactory
                  .getBeanClassLoader()
                  .loadClass("org.springframework.web.servlet.DispatcherServlet");

          // Now attempt to load our injected instrumentation class from the same class loader as
          // DispatcherServlet
          Class<?> clazz =
              dispatcherServletClass
                  .getClassLoader()
                  .loadClass(HealthHandlerMapping.class.getName());
          GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
          beanDefinition.setScope(SCOPE_SINGLETON);
          beanDefinition.setBeanClass(clazz);

          ((BeanDefinitionRegistry) beanFactory)
              .registerBeanDefinition("healthHandlerMapping", beanDefinition);
        } catch (ClassNotFoundException ignored) {
          // Ignore
        }
      }
    }
  }
}
