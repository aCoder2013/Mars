package com.song.core.init;

import com.song.core.CrudRepository;
import com.song.core.support.DefaultCrudRepository;

import org.springframework.beans.factory.FactoryBean;


/**
 * Created by song on 16/6/27.
 */
public class RepositoryFactoryBean implements FactoryBean {

  private Class<?> interfaceType;

  public RepositoryFactoryBean(Class<?> interfaceType) {
    this.interfaceType = interfaceType;
  }


  @Override
  public Object getObject() throws Exception {
    return ProxyFactory.getInstance().addInterface(this.interfaceType).setTarget(new DefaultCrudRepository<>())
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
