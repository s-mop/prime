package org.smop.prime.routing;

import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.loadbalancer.IRule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.commons.httpclient.HttpClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@Import({ HttpClientConfiguration.class})
@EnableDiscoveryClient
public class RoutingConfig {
    
    @Bean
    @ConditionalOnMissingBean
    public IRule ribbonRule() {
        PrimeFirstRule primeFirstRule = new PrimeFirstRule();
        primeFirstRule.initWithNiwsConfig(new DefaultClientConfigImpl());
        return primeFirstRule;
    }
}
