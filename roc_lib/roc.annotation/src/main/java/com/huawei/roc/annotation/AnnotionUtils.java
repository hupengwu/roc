
package com.huawei.roc.annotation;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnotionUtils {

    public static String getInfo(Class<?> cs) {
        String result = "";

        // 类上定义的字段
        Field[] declaredFields = cs.getDeclaredFields();
        for (Field field : declaredFields) {
            // 字段上是否有Name类型的注解
            if (field.isAnnotationPresent(Name.class)) {
                Name annotation = field.getAnnotation(Name.class);
                String value = annotation.value();
                result += (field.getName() + ":" + value + "\n");
            }
            // 字段上是否有Sex类型的注解
            if (field.isAnnotationPresent(Sex.class)) {
                Sex annotation = field.getAnnotation(Sex.class);
                String value = annotation.gender1().name();
                result += (field.getName() + ":" + value + "\n");
            }
        }
        return result;
    }

    public static void main(String[] args) {

        sgetMethod();
        String info = getInfo(Person.class);
        System.out.println(info);
    }

    public static void sgetMethod() {
        String file = "abc";
        Object ss = file;
        Class c = ss.getClass();
        Method[] m = c.getMethods();
        for (int i = 0; i < m.length; i++) {
            System.out.println(m[i].getName());
        }

    }

}
