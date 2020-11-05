package org.smop.red.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.List;

//@ReactiveFeignClient("red")
//@Component
public interface RedTalkClient {

    @GetMapping("hi")
    List<String> hi();

    @GetMapping("hello")
    String hello();

    @GetMapping("yo")
    Mono<List<String>> yo();
}
