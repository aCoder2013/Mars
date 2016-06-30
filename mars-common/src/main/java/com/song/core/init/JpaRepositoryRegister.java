package com.song.core.init;

import com.song.core.CrudRepository;
import com.song.core.DAO;
import com.song.core.RepositoryMetadata;
import com.song.core.RepositoryMetadataFactory;
import com.song.utils.Assert;
import com.song.utils.ClassUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;
import java.util.Set;

/**
 * Created by song on 16/6/24.
 */
@Configuration
@ComponentScan("com.song")
public class JpaRepositoryRegister implements BeanFactoryAware, ImportBeanDefinitionRegistrar {

  private Logger logger = LoggerFactory.getLogger(JpaRepositoryRegister.class);


  private BeanFactory beanFactory;

  @Override
  public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
    String[] basePackages = null;
    if (AutoConfigurationPackages.has(this.beanFactory)) {
      List<String> basePackageList = AutoConfigurationPackages.get(this.beanFactory);
      basePackages = basePackageList.toArray(new String[basePackageList.size()]);
    }
    Assert.notNull(basePackages);
    Set<Class<?>> classes = ClassUtils.getInstance()
            .findSubInterfaceOf(CrudRepository.class, basePackages)
            .withAnnotation(DAO.class)
            .getClasses();
    RepositoryMetadataFactory.fillWith(classes);
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
    this.beanFactory = beanFactory;
  }
}
