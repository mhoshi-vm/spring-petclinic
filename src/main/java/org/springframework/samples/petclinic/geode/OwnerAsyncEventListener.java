package org.springframework.samples.petclinic.geode;

import org.apache.geode.cache.asyncqueue.AsyncEvent;
import org.apache.geode.cache.asyncqueue.AsyncEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OwnerAsyncEventListener implements AsyncEventListener {

	private static final Logger logger = LoggerFactory.getLogger(OwnerAsyncEventListener.class);

	private final OwnerRepository ownerRepository;

	public OwnerAsyncEventListener(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

	@Override
	public boolean processEvents(List<AsyncEvent> events) {
		events.forEach(asyncEvent -> {
			Owner owner = (Owner) asyncEvent.getDeserializedValue();
			logger.info("Processing Employee {}...", owner.toString());
			ownerRepository.save(owner);
			logger.info("Processing Employee {}... Done!", owner.toString());
		});

		return true;
	}

}
