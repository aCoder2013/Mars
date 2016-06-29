package com.song.core.support;

import com.song.core.CrudRepository;
import com.song.core.RepositoryMetadata;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by song on 16/6/29.
 */
public class DefaultRepositoryMetadataTest {

  private RepositoryMetadata metadata;

  @Before
  public void setup() {
    metadata = new DefaultRepositoryMetadata(DemoRepository.class);
  }

  @Test
  public void test() throws Exception {
    assertEquals(Long.class,metadata.domainType());
    assertEquals(Integer.class,metadata.idType());
  }


  interface DemoRepository extends CrudRepository<Long, Integer> {

  }
}
