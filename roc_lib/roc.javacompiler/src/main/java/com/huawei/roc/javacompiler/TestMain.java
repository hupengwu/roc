/**
 * https://blog.csdn.net/qq_35232663/article/details/80023314
 */
package com.huawei.roc.javacompiler;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import sun.misc.ClassLoaderUtil;

/** 
 * java jar的动态加载和释放 
 */
public class TestMain {

    public static void main(String[] args) throws MalformedURLException, Exception {

        System.out.println(new File(".").getAbsolutePath());
        URLClassLoader urlClassLoader =
            new URLClassLoader(new URL[] {new URL("file:C:\\Users\\Administrator\\IdeaProjects\\test\\data.jar")});
        Class classStudentServiceImpl = urlClassLoader.loadClass("StudentServiceImpl");
        Method method = classStudentServiceImpl.getMethod("getName", new Class[] {});
        Constructor localConstructor = classStudentServiceImpl.getConstructor(new Class[] {});
        Object instance = localConstructor.newInstance(new Object[] {});
        Object ret = method.invoke(instance);
        System.out.println(ret);

        ClassLoaderUtil.releaseLoader(urlClassLoader);
        while (true) {
            Thread.sleep(1000);
            System.out.println("run");
        }

    }
}
