package com.song.core.init;

import com.song.core.CrudRepository;
import com.song.core.support.DefaultCrudRepository;
import com.song.utils.Assert;

import org.springframework.beans.factory.FactoryBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * Created by song on 16/6/27.
 */
public class RepositoryFactoryBean implements FactoryBean {

  private Class<?> interfaceType;

  private EntityManager entityManager;

  public RepositoryFactoryBean(Class<?> interfaceType) {
    this.interfaceType = interfaceType;
  }

  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Object getObject() throws Exception {
    Assert.notNull(this.entityManager, "EntityManager must not be null");
    return ProxyFactory.getInstance().addInterface(this.interfaceType).setTarget(new DefaultCrudRepository<>(entityManager))
            .getProxy();
  }

  @Override
  public Class<?> getObjectType() {
    return this.interfaceType != null ? this.interfaceType : CrudRepository.class;
  }

  @Override
  public boolean isSingleton() {
    return false;
  }


}
