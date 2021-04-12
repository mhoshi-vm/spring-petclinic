/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.system;

import org.apache.geode.cache.Cache;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.asyncqueue.AsyncEventQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.EnableCachingDefinedRegions;
import org.springframework.data.gemfire.wan.AsyncEventQueueFactoryBean;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerAsyncEventListener;
import org.springframework.samples.petclinic.vet.Vet;

/**
 * Cache configuration intended for caches providing the JCache API. This configuration
 * creates the used cache for the application and enables statistics that become
 * accessible via JMX.
 */

@Configuration(proxyBeanMethods = false)
@EnableCachingDefinedRegions
class CacheConfiguration {

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

	@Bean("Vets")
	ReplicatedRegionFactoryBean<Long, Vet> vetRegion(GemFireCache cache) {
		ReplicatedRegionFactoryBean<Long, Vet> regionFactoryBean = new ReplicatedRegionFactoryBean<>();
		regionFactoryBean.setCache(cache);

		return regionFactoryBean;
	}
}
