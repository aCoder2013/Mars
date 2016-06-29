package com.song.domain.sql.stat;

import com.song.domain.parameter.ParameterMap;
import com.song.domain.sql.Sql;

/**
 * Created by song on 16/6/18.
 */
public class StaticSql<T> implements Sql<T> {

  private String statement;

  private Class<T> domainType;

  private ParameterMap parameterMap;

  public StaticSql(String statement) {
    this.statement = statement;
  }

  public StaticSql(String statement, ParameterMap parameterMap) {
    this.statement = statement;
    this.parameterMap = parameterMap;
  }

  public StaticSql(String statement, Class<T> domainType, ParameterMap parameterMap) {
    this.statement = statement;
    this.domainType = domainType;
    this.parameterMap = parameterMap;
  }

  public String getSql() {
    return statement;
  }

  @Override
  public Class<T> domainType() {
    return domainType;
  }
  

  public ParameterMap getParameterMap() {
    return parameterMap;
  }
}
