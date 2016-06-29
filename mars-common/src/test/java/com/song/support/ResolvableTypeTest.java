package com.song.support;

import com.song.other.ABService;
import com.song.other.Service;

import org.junit.Test;

/**
 * Created by song on 16/6/20.
 */
public class ResolvableTypeTest {

  @Test
  public void getGenerics() throws Exception {
    ResolvableType type = ResolvableType.forClass(ABService.class).as(Service.class);
    System.out.println(type.getGeneric(0,0,0));
  }

  @Test
  public void as() {
    ResolvableType resolvableType = ResolvableType.forClass(ABService.class).as(Service.class);
    System.out.println(resolvableType);
  }

  @Test
  public void getInterfaces() {
    ResolvableType type = ResolvableType.forClass(ABService.class);
    ResolvableType[] types = type.getInterfaces();
    for (ResolvableType t : types) {
      System.out.println(t);
    }
  }
}
