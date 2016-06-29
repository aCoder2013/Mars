package com.song.utils;

import org.junit.Test;

/**
 * Created by song on 16/6/25.
 */
public class ClassUtilsTest {

  @Test
  public void testFindInterface() {
    System.out.println(ClassUtils.findInterfaces("com.song"));
  }

  @Test
  public void testFindSubClassesOfInterface() {
    System.out.println(ClassUtils.getInstance().findSubInterfaceOf(Iterable.class, "com.song"));
    System.out.println(ClassUtils.getInstance().findSubInterfaceOf(Object.class, "com.song"));
    System.out.println(ClassUtils.getInstance().findSubInterfaceOf(null, "com.song"));
  }

  interface A extends Iterable {
  }

  interface B extends A {
  }

  interface C extends B {
  }

  interface D extends C {
  }

}
