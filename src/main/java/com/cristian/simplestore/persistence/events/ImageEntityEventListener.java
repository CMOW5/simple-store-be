package com.cristian.simplestore.persistence.events;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.business.services.storage.ImageStorageService;
import com.cristian.simplestore.persistence.entities.Image;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

@Component
public class ImageEntityEventListener implements PostDeleteEventListener {
	
	private static final long serialVersionUID = -5065124746231626624L;

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired 
	ImageStorageService imageStorageService;

	@PostConstruct
	private void init() {
		SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
		EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
		registry.getEventListenerGroup(EventType.POST_DELETE).appendListener(this);
	}

	@Override
	public boolean requiresPostCommitHanding(EntityPersister persister) {
		return false;
	}

	@Override
	public void onPostDelete(PostDeleteEvent event) {
		final Object entity = event.getEntity();
		if (entity instanceof Image) {
			Image image = (Image) entity;
			if (!image.isAUrl()) {
				imageStorageService.delete(image.getName());
			}
		}
	}
}
