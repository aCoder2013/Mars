package com.song.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 16/6/24.
 */
public class AssertTest {

  @Test
  public void testNotEmpty(){
    List list = new ArrayList<>();
    Assert.notEmpty(list);
  }
}
