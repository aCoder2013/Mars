package com.song.core;

import java.io.Serializable;
import java.util.List;

/**
 * Created by song on 16/6/18.
 */
public interface CrudRepository<T, ID extends Serializable> {

  void save();

  T save(T entity);

  List<T> save(Iterable<T> entities);

  void update(T entity);

  void delete(ID id);

  void delete(T entity);

  void delete(Iterable<T> entities);

  void deleteAll();

  T find(ID id);

  List<T> findALl();

  long count();

}
