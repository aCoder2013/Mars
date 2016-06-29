package com.song.core;

import com.song.core.support.DefaultRepositoryMetadata;
import com.song.entity.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by song on 16/6/25.
 */
public class DefaultRepositoryMetadataTest {

  private RepositoryMetadata repositoryMetadata;

  @Before
  public void init() {
    repositoryMetadata = new DefaultRepositoryMetadata(TestRepository.class);
  }

  @Test
  public void test() {
    assertEquals(TestRepository.class, repositoryMetadata.repositoryType());
    assertEquals("testRepository", repositoryMetadata.name());
    assertEquals(User.class, repositoryMetadata.domainType());
    assertEquals(Integer.class, repositoryMetadata.idType());
  }
}
