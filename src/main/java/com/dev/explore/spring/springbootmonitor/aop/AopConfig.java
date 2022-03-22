package com.dev.explore.spring.springbootmonitor.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan("com.dev.explore")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Slf4j
public class AopConfig {
    @PostConstruct
    public void init() {
        log.info("Constructed AppConfig");
    }
}
