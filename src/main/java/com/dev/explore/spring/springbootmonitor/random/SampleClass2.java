package com.dev.explore.spring.springbootmonitor.random;

import kamon.Kamon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SampleClass2 {

    @Autowired
    private SampleClass1 sampleClass1;

    public void pc2Test2() {
        log.info("PC2 test2: {}, {}", Kamon.currentSpan().trace().id().string(), Kamon.currentSpan().id().string());
        pc2Test3();
    }

    public void pc2Test3() {
        log.info("PC2 test3: {}, {}", Kamon.currentSpan().trace().id().string(), Kamon.currentSpan().id().string());
        sampleClass1.test4();
    }
}
