package com.song.other;

import org.junit.Test;

import java.lang.reflect.Type;

/**
 * Created by song on 16/6/20.
 */
public class GenericTypeTest {


  @Test
  public void test(){
    Type[] types = ABService.class.getGenericInterfaces();
    for(Type type : types){
      System.out.println(type);
    }
    System.out.printf(ABService.class.getGenericSuperclass().getTypeName());
    ABService service = new ABService();
    TestInterface testInterface = service;
  }




}
