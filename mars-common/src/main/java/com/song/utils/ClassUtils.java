package com.song.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * Created by song on 16/6/24.
 */
public class ClassUtils {


  private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class);

  private Set<Class<?>> classes = null;

  private ClassUtils() {
  }

  public static ClassUtils getInstance() {
    return new ClassUtils();
  }

  /**
   * 递归在instance上查找指定的注解
   */
  public static <T extends Annotation> T findAnnotation(Class instance, Class<T> annotationType) {
    if (instance != null && instance != Object.class) {
      T t = (T) instance.getAnnotation(annotationType);
      if (t != null) {
        return t;
      } else {
        return findAnnotation(instance.getSuperclass(), annotationType);
      }
    }
    return null;
  }

  /**
   * 查找给定包下接口interfaceType的子孙类
   *
   * @param interfaceType 接口类型
   * @param packageNames  包
   */
  public ClassUtils findSubInterfaceOf(Class interfaceType, String... packageNames) {
    Set<Class<?>> interfaceSet = new HashSet<>();
    Set<Class<?>> classes = findInterfaces(packageNames);
    for (Class<?> clz : classes) {
      doFindSubInterface(interfaceType, interfaceSet, clz);
    }
    this.classes = interfaceSet;
    return this;
  }


  public ClassUtils withAnnotation(Class<? extends Annotation> annotationType) {
    Set<Class<?>> classSet = new HashSet<>();
    if (this.classes != null && annotationType != null) {
      classSet.addAll(classes.stream()
              .filter(clz -> clz != null && clz.isAnnotationPresent(annotationType))
              .collect(Collectors.toList()));
    }
    this.classes = classSet;
    return this;
  }

  public Set<Class<?>> getClasses() {
    return this.classes;
  }

  private static Class<?> doFindSubInterface(Class interfaceType, Set<Class<?>> interfaceSet, Class<?> clz) {
    if (clz != null) {
      Class<?>[] interfaces = clz.getInterfaces();
      for (Class<?> inter : interfaces) {
        if (inter != null) {
          if (inter.equals(interfaceType)) {
            interfaceSet.add(clz);
            return clz;
          } else {
            Class result = doFindSubInterface(interfaceType, interfaceSet, inter);
            if (result != null) {
              interfaceSet.add(clz);
            }
            return result;
          }
        }
      }
    }
    return null;
  }

  /**
   * 找到指定包下标记有相关注解的类
   */
  public static Set<Class<?>> findClassesWithAnnotation(Class<? extends Annotation> annotationType, String... packageNames) {
    Set<Class<?>> annotatedClasses = new HashSet<>();
    if (annotationType != null) {
      Set<Class<?>> classes = findClasses(packageNames);
      annotatedClasses.addAll(classes.stream().filter(clz -> clz.isAnnotationPresent(annotationType)).collect(Collectors.toList()));
    }
    return annotatedClasses;
  }

  public static Set<Class<?>> findInterfaces(String... packageNames) {
    Set<Class<?>> interfaces = new HashSet<>();
    Set<Class<?>> classes = findClasses(packageNames);
    for (Class<?> clz : classes) {
      if (clz != null && clz.isInterface()) {
        interfaces.add(clz);
      }
    }
    return interfaces;
  }

  /**
   * 找到指定包下所有的类
   */
  public static Set<Class<?>> findClasses(String... packageNames) {
    Set<Class<?>> classes = new HashSet<>();
    if (packageNames != null && packageNames.length > 0) {
      // TODO: 16/6/25  cache
      for (String packageName : packageNames) {
        try {
          if (StringUtils.isNotBlank(packageName)) {
            ClassLoader loader = getClassLoader();
            if (loader != null) {
              String path = packageName.replace(".", "/");
              Enumeration<URL> resources = loader.getResources(path);
              if (resources != null) {
                while (resources.hasMoreElements()) {
                  URL resource = resources.nextElement();
                  if (resource != null) {
                    String protocol = resource.getProtocol();
                    if (protocol != null) {
                      if (protocol.equals("file")) {
                        handleRegularFile(classes, path, resource.getPath());
                      } else if (protocol.equals("jar")) {
                        handleJarFile(classes, resource);
                      }
                    }
                  }
                }
              }
            }
          }
        } catch (IOException e) {
          logger.error("exception", e);
        }
      }
    }
    return classes;
  }

  /**
   * 处理JAR文件 将所有的.class文件添加到集合中
   */
  private static void handleJarFile(Set<Class<?>> classes, URL url) throws IOException {
    if (url != null && classes != null) {
      JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
      while (jarURLConnection != null) {
        JarFile jarFile = jarURLConnection.getJarFile();
        while (jarFile != null) {
          Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
          while (jarEntryEnumeration.hasMoreElements()) {
            JarEntry entry = jarEntryEnumeration.nextElement();
            if (entry != null) {
              String jarEntityName = entry.getName();
              if (jarEntityName != null && jarEntityName.endsWith(".class")) {
                String className = jarEntityName.substring(0, jarEntityName.lastIndexOf(".class")).replace("/", ".");
                classes.add(loadClass(className, false));
              }
            }
          }
        }
      }
    }
  }

  /**
   * 遍历packageName，将所有的.class文件添加到集合中
   */
  private static void handleRegularFile(Set<Class<?>> classes, String packageName, String path) {
    if (StringUtils.isNotBlank(path) && classes != null) {
      final File[] files = new File(path)
              .listFiles(pathname -> pathname.isDirectory() || (pathname.isFile() && pathname.getName().endsWith(".class")));
      if (files != null && files.length > 0) {
        for (File temp : files) {
          if (temp != null) {
            if (temp.isDirectory()) {
              if (StringUtils.isEmpty(packageName)) {
                handleRegularFile(classes, temp.getName(), path + "/" + temp.getName());
              } else {
                handleRegularFile(classes, packageName + "/" + temp.getName(), path + "/" + temp.getName());
              }
            } else if (temp.isFile()) {
              String className = null;
              className = temp.getName().substring(0, temp.getName().indexOf(".class"));
              classes.add(loadClass((packageName + "/" + className).replaceAll("/", "."), false));
            }
          }
        }
      }
    }
  }


  /**
   * 根据类名称加载类 根据isInitialized的值决定是否初始化
   */
  public static Class<?> loadClass(String className, boolean isInitialized) {
    Class cls = null;
    try {
      cls = Class.forName(className, isInitialized, getClassLoader());
    } catch (ClassNotFoundException e) {
      logger.error("Load Class Failure :" + className, e);
    }
    return cls;
  }

  public static ClassLoader getClassLoader() {
    ClassLoader loader = null;
    try {
      loader = Thread.currentThread().getContextClassLoader();
    } catch (Throwable e) {
      //Can't access? It's OK .
    }
    try {
      if (loader == null) {
        loader = ClassUtils.class.getClassLoader();
      }
    } catch (Throwable e) {
      //Still can't access? OK !
    }

    try {
      if (loader == null) {
        loader = ClassLoader.getSystemClassLoader();
      }
    } catch (Throwable e) {
      // well,bye bye .
    }
    return loader;
  }

}
