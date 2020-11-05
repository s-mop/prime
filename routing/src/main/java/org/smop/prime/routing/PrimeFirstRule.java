package org.smop.prime.routing;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
public class PrimeFirstRule extends ZoneAvoidanceRule {

    @Override
    public Server choose(Object key) {
        List<Server> servers = getLoadBalancer().getReachableServers();
        Optional<Server> any = servers.stream().filter(getServerPredicate()).findAny();
        return any.orElseGet(() -> super.choose(key));
    }

    private Predicate<Server> getServerPredicate() {
        return x -> {
            if (x instanceof NacosServer) {
                NacosServer s = (NacosServer) x;
                String tag = s.getMetadata().get("tag");
                return tag != null && tag.contains("prime");
            }
            return false;
        };
    }
}
