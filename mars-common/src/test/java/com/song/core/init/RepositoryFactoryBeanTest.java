package com.song.core.init;

import com.song.core.CrudRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by song on 16/6/29.
 */
public class RepositoryFactoryBeanTest {

  private RepositoryFactoryBean repositoryFactoryBean;

  @Before
  public void setUp() {
    repositoryFactoryBean = new RepositoryFactoryBean(TestRepository.class);
  }

  @Test
  public void testGetObject() throws Exception {
    TestRepository testRepository = (TestRepository) repositoryFactoryBean.getObject();
    testRepository.save();
  }


  @Test
  public void testGetObjectType() throws Exception {
    Assert.assertEquals(TestRepository.class, repositoryFactoryBean.getObjectType());
  }

  interface TestRepository extends CrudRepository {

  }
}
