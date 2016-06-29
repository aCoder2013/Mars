package com.song.core;

import com.song.core.init.JpaRepositoryRegister;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by song on 16/6/24.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(JpaRepositoryRegister.class)
public @interface EnableMars {


  /**
   * 要扫描的包
   */
  String[] value() default {};


}
