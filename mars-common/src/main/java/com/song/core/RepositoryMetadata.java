package com.song.core;

import java.io.Serializable;

/**
 * Created by song on 16/6/25.
 */
public interface RepositoryMetadata {

  String name();

  Class<?> repositoryType();

  Class<?> domainType();

  Class<? extends Serializable> idType();
}
