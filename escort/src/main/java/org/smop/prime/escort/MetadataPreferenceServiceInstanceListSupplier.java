package org.smop.prime.escort;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.DelegatingServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MetadataPreferenceServiceInstanceListSupplier
		extends DelegatingServiceInstanceListSupplier {

	public MetadataPreferenceServiceInstanceListSupplier(ServiceInstanceListSupplier delegate) {
		super(delegate);
	}

	@Override
	public Flux<List<ServiceInstance>> get() {
		return getDelegate().get().map(this::filteredByMetadata);
	}

	private List<ServiceInstance> filteredByMetadata(List<ServiceInstance> serviceInstances) {
		List<ServiceInstance> collect = serviceInstances.stream()
				.filter(getMetadataPrimeTagPredicate())
				.collect(Collectors.toList());
		
		if (collect.isEmpty())
			return serviceInstances;
		else
			return collect;
	}

	private Predicate<ServiceInstance> getMetadataPrimeTagPredicate() {
		return x -> {
			String tag = x.getMetadata().get("tag");
			return tag != null && tag.contains("prime");
		};
	}

}
