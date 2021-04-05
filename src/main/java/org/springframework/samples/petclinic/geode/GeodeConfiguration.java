package org.springframework.samples.petclinic.geode;

import org.apache.geode.cache.Cache;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.asyncqueue.AsyncEventQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.wan.AsyncEventQueueFactoryBean;
import org.springframework.samples.petclinic.owner.Owner;

@Configuration
public class GeodeConfiguration {

	@Bean
	AsyncEventQueueFactoryBean ownerAsyncEventQueue(GemFireCache cache, OwnerAsyncEventListener asyncEventListener) {
		AsyncEventQueueFactoryBean queueFactoryBean = new AsyncEventQueueFactoryBean((Cache) cache);
		queueFactoryBean.setAsyncEventListener(asyncEventListener);

		return queueFactoryBean;
	}

	@Bean("Owner")
	ReplicatedRegionFactoryBean<Long, Owner> ownersRegion(GemFireCache cache, AsyncEventQueue asyncEventQueue) {
		ReplicatedRegionFactoryBean<Long, Owner> regionFactoryBean = new ReplicatedRegionFactoryBean<>();
		regionFactoryBean.setCache(cache);
		regionFactoryBean.setPersistent(false);
		regionFactoryBean.setAsyncEventQueues(new AsyncEventQueue[] { asyncEventQueue });

		return regionFactoryBean;
	}

}
