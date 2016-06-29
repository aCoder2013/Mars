package com.song.core;

/**
 * Created by song on 16/6/19.
 */
public interface EntityMetadata<T> {

  Class<T> getJavaType();
}
