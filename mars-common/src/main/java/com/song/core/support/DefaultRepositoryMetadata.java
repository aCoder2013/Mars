package com.song.core.support;

import com.song.core.CrudRepository;
import com.song.core.RepositoryMetadata;
import com.song.support.ResolvableType;
import com.song.utils.Assert;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * Created by song on 16/6/25.
 */
public class DefaultRepositoryMetadata implements RepositoryMetadata {

  private static final String MUST_BE_A_REPOSITORY = String.format("Given type must be assignable to %s!",
          CrudRepository.class);

  private String name;

  private Class<?> repositoryType;

  private Class<?> domainType;

  private Class<? extends Serializable> idType;

  public DefaultRepositoryMetadata(Class<?> repositoryType) {
    Assert.notNull(repositoryType);
    Assert.isTrue(CrudRepository.class.isAssignableFrom(repositoryType), MUST_BE_A_REPOSITORY);
    this.repositoryType = repositoryType;
    resolve();
  }

  private void resolve() {
    this.name = StringUtils.uncapitalize(repositoryType.getSimpleName());
    ResolvableType resolvableType = ResolvableType.forClass(repositoryType).as(CrudRepository.class);
    this.domainType = resolvableType.getGeneric(0).getResolved();
    this.idType = (Class<? extends Serializable>) resolvableType.getGeneric(1).getResolved();
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public Class<?> repositoryType() {
    return this.repositoryType;
  }

  @Override
  public Class<?> domainType() {
    return this.domainType;
  }

  @Override
  public Class<? extends Serializable> idType() {
    return this.idType;
  }
}
