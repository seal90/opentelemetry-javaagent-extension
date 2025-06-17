# build [javaagent-extension](javaagent-extension)
# config start args modify AGENT_PATH PROJECT_PATH
```shell
JAVA_TOOL_OPTIONS="-javaagent:AGENT_PATH/opentelemetry-javaagent-2.16.0.jar";OTEL_JAVAAGENT_EXTENSIONS=PROJECT_PATH/opentelemetry-javaagent-extension/javaagent-extension/target/javaagent-extension-1.0-SNAPSHOT.jar;OTEL_TRACES_EXPORTER=none;OTEL_METRICS_EXPORTER=prometheus,logging-otlp;OTEL_LOGS_EXPORTER=none
```
# start [HelloWorldApplication.java](spring-web/src/main/java/io/github/seal90/opentelemetry/web/HelloWorldApplication.java)

```text
java.lang.ClassNotFoundException: org.springframework.web.servlet.v3_1.HealthCheckHandlerMappingFilter
	at java.net.URLClassLoader.findClass(URLClassLoader.java:387)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:418)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:352)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:351)
	at org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext.postProcessBeanFactory(AnnotationConfigServletWebServerApplicationContext.java:201)
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:529)
	at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:141)
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:747)
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:397)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:315)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1226)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1215)
	at io.github.seal90.opentelemetry.web.HelloWorldApplication.main(HelloWorldApplication.java:13)
java.lang.ClassNotFoundException: org.springframework.web.servlet.v3_1.HealthCheckHandlerMappingFilter
	at java.net.URLClassLoader.findClass(URLClassLoader.java:387)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:418)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:352)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:351)
	at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.postProcessBeanFactory(ServletWebServerApplicationContext.java:133)
	at org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext.postProcessBeanFactory(AnnotationConfigServletWebServerApplicationContext.java:201)
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:529)
	at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:141)
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:747)
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:397)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:315)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1226)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1215)
	at io.github.seal90.opentelemetry.web.HelloWorldApplication.main(HelloWorldApplication.java:13)
```
