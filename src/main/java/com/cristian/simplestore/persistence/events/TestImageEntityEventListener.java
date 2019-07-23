package com.cristian.simplestore.persistence.events;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.infrastructure.adapters.repository.entities.ImageEntity;
import com.cristian.simplestore.infrastructure.adapters.storage.ImageStorageService;

@Component
public class TestImageEntityEventListener implements PostDeleteEventListener {

  private static final long serialVersionUID = -5065124746231626624L;

  @Autowired
  private EntityManagerFactory entityManagerFactory;
  
  @Autowired
  ImageStorageService imageStorageService;
  
  @PostConstruct
  private void init() {
    SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
    EventListenerRegistry registry =
        sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
    registry.getEventListenerGroup(EventType.POST_DELETE).appendListener(this);
  }

  @Override
  public boolean requiresPostCommitHanding(EntityPersister persister) {
    return false;
  }

  @Override
  public void onPostDelete(PostDeleteEvent event) {
    final Object entity = event.getEntity();
    if (entity instanceof ImageEntity) {
    	ImageEntity image = (ImageEntity) entity;
    	imageStorageService.delete(image.getName());
//    	logger.error("An ERROR Message = image with id deleted = " + image.getId());
//      if (!image.isUrl()) {
//        imageStorageService.delete(image.getName());
//      }
    }
  }
  
  
}
