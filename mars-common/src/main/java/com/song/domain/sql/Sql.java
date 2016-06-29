package com.song.domain.sql;

import com.song.domain.parameter.ParameterMap;

/**
 * Created by song on 16/6/18.
 */
public interface Sql<T> {

  String getSql();

  Class<T> domainType();

  ParameterMap getParameterMap();
}
