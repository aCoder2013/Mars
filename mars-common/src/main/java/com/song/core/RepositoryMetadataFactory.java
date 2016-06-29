package com.song.core;

import com.song.core.support.DefaultRepositoryMetadata;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by song on 16/6/25.
 */
public class RepositoryMetadataFactory {


  private static final Map<String, RepositoryMetadata> metadataHolder = new ConcurrentHashMap<>();


  public RepositoryMetadataFactory() {
  }


  public static RepositoryMetadata get(String name) {
    return metadataHolder.get(name);
  }

  public static Set<RepositoryMetadata> getAll() {
    Set<RepositoryMetadata> classes = new HashSet<>(metadataHolder.values());
    return classes;
  }

  public static void put(Class<?> clz) {
    if (clz != null) {
      RepositoryMetadata metadata = new DefaultRepositoryMetadata(clz);
      metadataHolder.put(metadata.name(), metadata);
    }
  }


  public static void put(RepositoryMetadata metadata) {
    if (metadata != null) {
      metadataHolder.put(metadata.name(), metadata);
    }
  }

  public static void fillWith(Set<Class<?>> classes) {
    if (classes != null && classes.size() > 0) {
      for (Class<?> clz : classes) {
        RepositoryMetadata metadata = new DefaultRepositoryMetadata(clz);
        metadataHolder.put(metadata.name(), metadata);
      }
    }
  }

}
