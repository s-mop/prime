package org.smop.blue;

import org.smop.red.client.RedFluxClient;
import org.smop.red.client.RedTalkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class TalkService {

    public static final Random RANDOM = new Random();

    @Autowired
    private RedFluxClient redTalkClient;

    @Autowired
    private DiscoveryClient discoveryClient;
    
    @GetMapping("blank")
    public Mono<String> blank() {
        return Mono.just("blank");
    }
    
    @GetMapping("blank2")
    public String blank2() {
        return "blank";
    }

    @GetMapping(value = "drip", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<String> drip() {
        return Flux.fromStream(IntStream.range(1, 5).mapToObj(i -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ignored) {
                    }
                    return "flux data--" + i;
                }));
    }
//
//    @GetMapping("hi")
//    public String hi() {
//        List<String> hi = redTalkClient.hi();
//        return "hi Im blue" + RANDOM.nextInt(2000) + " " + String.join(",", hi);
//    }

    @GetMapping("yo")
    public Mono<List<String>> yo() {
        return redTalkClient.yo();
    }
//
//    @GetMapping("hello")
//    public String hello() {
//        String hello = redTalkClient.hello();
//        return "hello Im blue" + RANDOM.nextInt(2000) + " " + hello;
//    }

    @GetMapping("client")
    public List<List<ServiceInstance>> getClients() {
        List<String> services = discoveryClient.getServices();
        List<List<ServiceInstance>> collect = services.stream().map(x -> discoveryClient.getInstances(x)).collect(Collectors.toList());
        return collect;
    }
}
