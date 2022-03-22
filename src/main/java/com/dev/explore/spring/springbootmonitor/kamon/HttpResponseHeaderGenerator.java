package com.dev.explore.spring.springbootmonitor.kamon;

import kamon.context.Context;
import kamon.instrumentation.http.HttpServerResponseHeaderGenerator;
import lombok.extern.slf4j.Slf4j;
import scala.collection.immutable.Map;

@Slf4j
public class HttpResponseHeaderGenerator implements HttpServerResponseHeaderGenerator {

    public HttpResponseHeaderGenerator() {
      log.info("HttpResponseHeaderGenerator initialized");
    }

    @Override
    public Map<String, String> headers(Context context) {
        return new Map.Map1("trace-id", context.kamon$context$Context$$_span().trace().id().string());
    }
}
