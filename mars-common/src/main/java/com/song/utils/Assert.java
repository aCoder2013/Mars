package com.song.utils;

import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * Created by song on 16/6/19.
 */
public abstract class Assert {

  public static void notEmpty(Object object) {
    notEmpty(object, "[Assertion failed] - this argument is required;it must not be null or empty");
  }

  public static void notEmpty(Object obj, String message) {
    notNull(obj);
    if (obj.getClass().isArray()) {
      Object[] objects = (Object[]) obj;
      if (objects == null || objects.length <= 0) {
        throw new IllegalArgumentException(message);
      }
    }
    if (obj instanceof Collection) {
      Collection collection = (Collection) obj;
      if (collection == null || collection.size() <= 0) {
        throw new IllegalArgumentException(message);
      }
    }
    if (StringUtils.isEmpty(obj)) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void notNull(Object object) {
    notNull(object, "[Assertion failed] - this argument is required; it must not be null");
  }

  public static void notNull(Object object, String message) {
    if (object == null) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void isTrue(boolean expression, String message) {
    if (!expression) {
      throw new IllegalArgumentException(message);
    }
  }
}
