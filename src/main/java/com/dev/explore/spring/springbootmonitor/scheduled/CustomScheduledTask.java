package com.dev.explore.spring.springbootmonitor.scheduled;

import com.dev.explore.spring.springbootmonitor.random.SampleClass1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@Slf4j
public class CustomScheduledTask {

    @Autowired
    private SampleClass1 sampleClass1;

    @Scheduled(fixedDelay = 500000)
    public void alive() {
        log.info("I'm still alive, cheers!");
        sampleClass1.test2();
    }
}
