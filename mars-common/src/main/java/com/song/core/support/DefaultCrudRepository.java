package com.song.core.support;

import com.song.core.CrudRepository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

/**
 * Created by song on 16/6/22.
 */
public class DefaultCrudRepository<T, ID extends Serializable> implements CrudRepository<T, ID> {

  private EntityManager entityManager;

  public DefaultCrudRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public T save(T entity) {
    this.entityManager.persist(entity);
    return entity;
  }

  @Override
  public List<T> save(Iterable<T> entities) {
    return null;
  }

  @Override
  public void update(T entity) {

  }

  @Override
  public void delete(ID id) {

  }

  @Override
  public void delete(T entity) {

  }

  @Override
  public void delete(Iterable<T> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public T find(ID id) {
    return null;
  }

  @Override
  public List<T> findALl() {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }


  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  protected void finalize() throws Throwable {
  }

  @Override
  public String toString() {
    return "DefaultCrudRepository{}";
  }

}
