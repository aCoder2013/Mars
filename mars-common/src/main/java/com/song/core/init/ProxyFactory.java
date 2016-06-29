package com.song.core.init;

import com.song.utils.ClassUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 16/6/27.
 */
public class ProxyFactory implements InvocationHandler {


  private Object target;

  private List<Class> interfaces = new ArrayList<>();


  public ProxyFactory addInterface(Class inter) {
    if (inter != null && inter.isInterface()) {
      interfaces.add(inter);
    }
    return this;
  }

  public ProxyFactory setTarget(Object target) {
    this.target = target;
    return this;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Object result = method.invoke(target, args);
    return result;
  }


  public Object getProxy() {
    if (this.target == null || this.interfaces.size() <= 0) {
      return null;
    }
    return Proxy.newProxyInstance(ClassUtils.getClassLoader(),
            this.interfaces.toArray(new Class[this.interfaces.size()]), this);
  }

  public static ProxyFactory getInstance() {
    return new ProxyFactory();
  }

}
