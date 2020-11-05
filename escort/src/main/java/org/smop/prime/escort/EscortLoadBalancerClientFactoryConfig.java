package org.smop.prime.escort;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.ConditionalOnReactiveDiscoveryEnabled;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientSpecification;
import org.springframework.cloud.loadbalancer.config.LoadBalancerAutoConfiguration;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration(proxyBeanMethods = false)
@EnableDiscoveryClient
@AutoConfigureBefore(LoadBalancerAutoConfiguration.class)
@ConditionalOnReactiveDiscoveryEnabled
public class EscortLoadBalancerClientFactoryConfig {

    @ConditionalOnMissingBean
    @Bean
    public LoadBalancerClientFactory loadBalancerClientFactory() {
        LoadBalancerClientFactory clientFactory = new LoadBalancerClientFactory();
        ArrayList<Class<?>> routingConfigs = new ArrayList<>();
        routingConfigs.add(ServiceInstanceSupplierConfig.class);
        ArrayList<LoadBalancerClientSpecification> configurations = new ArrayList<>();
        configurations.add(new LoadBalancerClientSpecification("default.routing", routingConfigs.toArray(new Class[0])));
        clientFactory.setConfigurations(configurations);
        return clientFactory;
    }
}
