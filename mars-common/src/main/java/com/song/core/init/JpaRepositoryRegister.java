package com.song.core.init;

import com.song.core.CrudRepository;
import com.song.core.DAO;
import com.song.core.EnableMars;
import com.song.core.RepositoryMetadata;
import com.song.core.RepositoryMetadataFactory;
import com.song.utils.Assert;
import com.song.utils.ClassUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * Created by song on 16/6/24.
 */
@Component
@ComponentScan("com.song")
public class JpaRepositoryRegister implements BeanFactoryAware, ImportBeanDefinitionRegistrar {

  private Logger logger = LoggerFactory.getLogger(JpaRepositoryRegister.class);

  private ListableBeanFactory beanFactory;


  public void init() {
    Map<String, Object> map = this.beanFactory.getBeansWithAnnotation(EnableMars.class);

    if (map == null || map.size() > 1) {
      throw new IllegalArgumentException("The number of EnableMars is not right");
    }
    for (String name : map.keySet()) {
      Object config = map.get(name);
      EnableMars enableMars = ClassUtils.findAnnotation(config.getClass(), EnableMars.class);
      Assert.notNull(enableMars);
      Assert.notEmpty(enableMars.value());
      String[] basePackages = enableMars.value();
      Set<Class<?>> classes = ClassUtils.getInstance()
              .findSubInterfaceOf(CrudRepository.class, basePackages)
              .withAnnotation(DAO.class)
              .getClasses();
      RepositoryMetadataFactory.fillWith(classes);

    }
  }


  @Override
  public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
    init();
    Set<RepositoryMetadata> metadataSet = RepositoryMetadataFactory.getAll();
    for (RepositoryMetadata metadata : metadataSet) {
      if (metadata != null) {
        BeanDefinitionBuilder beanDefinitionBuilder = null;
        try {
          beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(new RepositoryFactoryBean(metadata.repositoryType()).getClass());
          beanDefinitionBuilder.addConstructorArgValue(metadata.repositoryType());
        } catch (Exception e) {
          logger.error("Generic BeanDefinition Error ", e);
        }
        registry.registerBeanDefinition(metadata.name(), beanDefinitionBuilder.getBeanDefinition());
      }
    }
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = (ListableBeanFactory) beanFactory;
  }
}
