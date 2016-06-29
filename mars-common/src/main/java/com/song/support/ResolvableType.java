package com.song.support;

import com.song.utils.Assert;
import com.song.utils.ObjectUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by song on 16/6/20.
 */
public class ResolvableType {

  private static final ResolvableType NONE = new ResolvableType(Object.class);
  private static final ResolvableType[] EMPTY_ARRAY = new ResolvableType[0];

  private final Type type;

  private Class<?> resolved;

  private ResolvableType[] generics;
  private ResolvableType[] interfaces;

  public ResolvableType(Type type) {
    Assert.notNull(type);
    this.type = type;
    this.resolved = resolveClass();
    generics = null;
    interfaces = null;
  }

  public ResolvableType(Class<?> resolved) {
    Assert.notNull(resolved);
    this.resolved = resolved;
    this.type = resolved;
  }

  public static ResolvableType forType(Type type) {
    return new ResolvableType(type);
  }


  public static ResolvableType[] forTypes(Type[] types) {
    if (types != null) {
      ResolvableType[] resolvableTypes = new ResolvableType[types.length];
      for (int i = 0; i < resolvableTypes.length; i++) {
        resolvableTypes[i] = ResolvableType.forType(types[i]);
      }
      return resolvableTypes;
    }
    return EMPTY_ARRAY;
  }

  public static ResolvableType forClass(Class<?> clz) {
    return new ResolvableType(clz);
  }

  public ResolvableType as(Class<?> clz) {
    if (clz == null) {
      return NONE;
    }
    if (ObjectUtils.nullSafeEquals(resolved, clz)) {
      return this;
    }
    ResolvableType[] interfaceAsTypes = getInterfaces();
    for (ResolvableType interfaceAsType : interfaceAsTypes) {
      ResolvableType t = interfaceAsType.as(clz);
      if (t != NONE) {
        return t;
      }
    }
    return resolveType().as(clz);
  }

  /**
   * 根据type解析Class类型
   */
  private Class<?> resolveClass() {
    if (this.type instanceof Class) {
      return (Class) this.type;
    }
    return resolveType().resolveClass();
  }


  /**
   * 解析类型
   */
  public ResolvableType resolveType() {
    if (this.type instanceof Class) {
      return forClass((Class<?>) type);
    }
    if (type instanceof ParameterizedType) {
      Type raw = ((ParameterizedType) type).getRawType();
      return forType(raw);
    }
    return NONE;
  }


  public Type getType() {
    return type;
  }

  public Class<?> getResolved() {
    return resolved;
  }

  public ResolvableType getGeneric(int... indexes) {
    if (indexes == null || indexes.length == 0) {
      return getGenerics()[0];
    }
    ResolvableType generic = this;
    try {
      for (int i = 0; i < indexes.length; i++) {
        generic = generic.getGenerics()[indexes[i]];
      }
    } catch (IndexOutOfBoundsException e) {
      return NONE;
    }
    return generic;
  }

  public ResolvableType[] getGenerics() {
    if (this.type == null) {
      return EMPTY_ARRAY;
    }
    if (this.generics == null) {
      if (this.type instanceof Class) {
        Type[] variables = ((Class) this.type).getTypeParameters();
        this.generics = forTypes(variables);
      } else if (this.type instanceof ParameterizedType) {
        Type[] types = ((ParameterizedType) this.type).getActualTypeArguments();
        this.generics = forTypes(types);
      } else {
        this.generics = resolveType().getGenerics();
      }
    }
    return this.generics;
  }

  public ResolvableType[] getInterfaces() {
    if (interfaces != null) {
      return interfaces;
    }
    Type[] interfaceTypes = resolved.getGenericInterfaces();
    return forTypes(interfaceTypes);
  }

  public void setInterfaces(ResolvableType[] interfaces) {
    this.interfaces = interfaces;
  }


  @Override
  public String toString() {
    if (this == NONE) {
      return "?";
    }
    return "ResolvableType{" +
            "type=" + type +
            ", resolved=" + resolved +
            '}';
  }
}
