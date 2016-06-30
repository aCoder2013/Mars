package com.song.core.init;

import com.song.utils.Assert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;

/**
 * Created by song on 16/6/29.
 */
@Configuration
@AutoConfigureAfter(HibernateJpaAutoConfiguration.class)
@ConditionalOnClass(LocalContainerEntityManagerFactoryBean.class)
@ConditionalOnBean(LocalContainerEntityManagerFactoryBean.class)
@EnableConfigurationProperties({JpaProperties.class, MarsProperties.class})
@Import(JpaRepositoryRegister.class)
public class MarsAutoConfigure {

  private static final Logger logger = LoggerFactory.getLogger(MarsAutoConfigure.class);

  @Autowired
  private MarsProperties marsProperties;

  @Autowired
  private LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean;


  @Bean
  @ConditionalOnMissingBean
  public EntityManagerFactory entityManager() {
    Assert.notNull(this.localContainerEntityManagerFactoryBean);
    EntityManagerFactory factory = this.localContainerEntityManagerFactoryBean.getObject();
    return factory;
  }
}
