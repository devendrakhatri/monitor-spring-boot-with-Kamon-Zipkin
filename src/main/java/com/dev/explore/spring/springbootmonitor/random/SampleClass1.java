package com.dev.explore.spring.springbootmonitor.random;

import kamon.Kamon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SampleClass1 {

    @Autowired
    private SampleClass2 sampleClass2;

    public static void test1() {
        log.info("test1: {}, {}", Kamon.currentSpan().trace().id().string(), Kamon.currentSpan().id().string());
    }

    public void test2() {
        log.info("test2: {}, {}", Kamon.currentSpan().trace().id().string(), Kamon.currentSpan().id().string());
        test3();
        sampleClass2.pc2Test2();
    }

    public void test3() {
        log.info("test3: {}, {}", Kamon.currentSpan().trace().id().string(), Kamon.currentSpan().id().string());
    }

    public void test4() {
        log.info("test4: {}, {}", Kamon.currentSpan().trace().id().string(), Kamon.currentSpan().id().string());
    }
}
