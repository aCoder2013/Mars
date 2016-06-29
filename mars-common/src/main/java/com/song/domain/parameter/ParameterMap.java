package com.song.domain.parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by song on 16/6/18.
 */
public class ParameterMap {

  public static final ParameterMap EMPTY = new ParameterMap();

  private Class<?> parameterClass;

  private List<Object> params = new ArrayList<Object>();

  private Map<String, Integer> paramIndex = new HashMap<String, Integer>();


  public Class<?> getParameterClass() {
    return parameterClass;
  }

  public void setParameterClass(Class<?> parameterClass) {
    this.parameterClass = parameterClass;
  }

  public List<Object> getParams() {
    return params;
  }

  public void setParams(List<Object> params) {
    this.params = params;
  }

  public Map<String, Integer> getParamIndex() {
    return paramIndex;
  }

  public void setParamIndex(Map<String, Integer> paramIndex) {
    this.paramIndex = paramIndex;
  }


}
