package com.dev.explore.spring.springbootmonitor.kamon;

import kamon.Kamon;
import kamon.trace.ConstantSampler;
import kamon.trace.Sampler;
import kamon.trace.Trace.SamplingDecision;
import kamon.util.Filter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MySampler implements Sampler {

    @Override
    public SamplingDecision decide(Operation operation) {

        Filter filter = Kamon.filter("kamon.util.filters.my-custom-filter");

        boolean sample = filter.accept(operation.operationName());
        SamplingDecision samplingDecision = sample
                ? ConstantSampler.Always().decide(operation)
                : ConstantSampler.Never().decide(operation);

        log.debug("Operation name: {}, decision: {}", operation.operationName(), samplingDecision);
        return samplingDecision;
    }
}
