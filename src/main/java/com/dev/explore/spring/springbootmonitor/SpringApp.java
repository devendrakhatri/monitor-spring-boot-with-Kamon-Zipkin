package com.dev.explore.spring.springbootmonitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import kamon.Kamon;
import kamon.trace.Identifier;
import kamon.trace.Span;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.scheduling.annotation.EnableScheduling;
import scala.Function0;

@SpringBootApplication
@EnableScheduling
@EnableAdminServer
public class SpringApp {

	public static void main(String[] args) {
		Kamon.init();
		Function0<Void> startup = () -> {
			SpringApplication application = new SpringApplication(SpringApp.class);
			application.setApplicationStartup(new BufferingApplicationStartup(2048));
			application.run(args);
			return null;
		};
		Span span = Kamon.spanBuilder("startup").traceId(Identifier.Empty()).name("startup").start();
		Kamon.runWithSpan(span, true, startup);
	}
}
