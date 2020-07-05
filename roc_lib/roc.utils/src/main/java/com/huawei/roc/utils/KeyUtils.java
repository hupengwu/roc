/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 根据指定的Key将列表转换为Map
 * @author h00163887
 * 
 */
public class KeyUtils {
    /**
     * 将列表转换哈希表，其中ID是Integer类型
     * @param objList 列表
     * @param getKeyMathName 元素中的Key的get方法，比如列表中的元素含有getId()方法，那么"getId"
     * @return 哈希表
     */
    public static <T> Map<Integer, T> list2mapByIntegerKey(List<T> objList, String getKeyMathName) {
        return list2mapByKey(objList, Integer.class, getKeyMathName);
    }

    /**
     * 将列表转换哈希表，其中ID是String类型
     * @param objList 列表
     * @param getKeyMathName 元素中的Key的get方法，比如列表中的元素含有getId()方法，那么"getId"
     * @return 哈希表
     */
    public static <T> Map<String, T> list2mapByStringKey(List<T> objList, String getKeyMathName) {
        return list2mapByKey(objList, String.class, getKeyMathName);
    }

    /**
     * 根据Key生成Map，该方法是是具体类的函数，（不具备多态能力，不是反射，速度很快）
     * @param objList
     * @param clazz
     * @param method 使用obj.getClass().getMethod("getTnlKey", new Class[0])获取Method
     * @return
     */
    public static <K, T> Map<K, T> list2mapByKeyAndFinalMethod(List<T> objList, Class<K> clazz, Method method) {
        try {
            Map<K, T> uid2deviceMap = new HashMap<K, T>();
            for (T obj : objList) {
                // 接下来就该执行该方法了，第一个参数是具体调用该方法的对象, 第二个参数是执行该方法的具体参数
                Object keyObject = method.invoke(obj, new Object[0]);
                if (clazz.isInstance(keyObject)) {
                    K key = clazz.cast(keyObject);
                    uid2deviceMap.put(key, obj);
                }
            }

            return uid2deviceMap;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }

    /**
     * 根据Key生成Map，该方法是是具体类的函数，（具有多态能力，使用反射机制，速度很慢）
     * @param objList
     * @param clazz
     * @param getKeyMathName
     * @return
     */
    public static <K, T> Map<K, T> list2mapByKey(List<T> objList, Class<K> clazz, String getKeyMathName) {
        try {
            Map<K, T> uid2deviceMap = new HashMap<K, T>();
            for (T obj : objList) {
                // 先获取相应的method对象,getMethod第一个参数是方法名，第二个参数是该方法的参数类型，
                Method method = obj.getClass().getMethod(getKeyMathName, new Class[0]);

                // 接下来就该执行该方法了，第一个参数是具体调用该方法的对象, 第二个参数是执行该方法的具体参数
                Object keyObject = method.invoke(obj, new Object[0]);
                if (clazz.isInstance(keyObject)) {
                    K key = clazz.cast(keyObject);
                    uid2deviceMap.put(key, obj);
                }
            }

            return uid2deviceMap;
        } catch (NoSuchMethodException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }

    /**
     * 根据Key生成Map（不具备多态能力，不是反射，速度很快）
     * @param objList
     * @param clazz
     * @param method
     * @return
     */
    public static <K, T> Map<K, List<T>> list2listmapByKeyAndFinalMethod(List<T> objList, Class<K> clazz,
        Method method) {
        try {
            Map<K, List<T>> uid2deviceMap = new HashMap<K, List<T>>();
            for (T obj : objList) {
                // 接下来就该执行该方法了，第一个参数是具体调用该方法的对象, 第二个参数是执行该方法的具体参数
                Object keyObject = method.invoke(obj, new Object[0]);
                if (clazz.isInstance(keyObject)) {
                    K key = clazz.cast(keyObject);

                    List<T> tmpList = uid2deviceMap.get(key);
                    if (tmpList == null) {
                        tmpList = new ArrayList<T>();
                        uid2deviceMap.put(key, tmpList);
                    }

                    tmpList.add(obj);
                }
            }

            return uid2deviceMap;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }

    /**
     * 根据Key生成Map，该方法是是具体类的函数，（具有多态能力，使用反射机制，速度很慢）
     * @param objList
     * @param clazz
     * @param getKeyMathName
     * @return
     */
    public static <K, T> Map<K, List<T>> list2listmapByKey(List<T> objList, Class<K> clazz, String getKeyMathName) {
        try {
            Map<K, List<T>> uid2deviceMap = new HashMap<K, List<T>>();
            for (T obj : objList) {
                // 先获取相应的method对象,getMethod第一个参数是方法名，第二个参数是该方法的参数类型，
                Method method = obj.getClass().getMethod(getKeyMathName, new Class[0]);

                // 接下来就该执行该方法了，第一个参数是具体调用该方法的对象, 第二个参数是执行该方法的具体参数
                Object keyObject = method.invoke(obj, new Object[0]);
                if (clazz.isInstance(keyObject)) {
                    K key = clazz.cast(keyObject);

                    List<T> tmpList = uid2deviceMap.get(key);
                    if (tmpList == null) {
                        tmpList = new ArrayList<T>();
                        uid2deviceMap.put(key, tmpList);
                    }

                    tmpList.add(obj);
                }
            }

            return uid2deviceMap;
        } catch (NoSuchMethodException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }

    public static <K, T> List<K> getIDListByKey(List<T> objList, Class<K> clazz, String getKeyMathName) {
        try {
            Method method = null;
            List<K> uidList = new ArrayList<K>();
            for (T obj : objList) {
              //  if (method == null) 
                {
                    // 先获取相应的method对象,getMethod第一个参数是方法名，第二个参数是该方法的参数类型，
                    method = obj.getClass().getMethod(getKeyMathName, new Class[0]);
                }

                // 接下来就该执行该方法了，第一个参数是具体调用该方法的对象, 第二个参数是执行该方法的具体参数
                Object keyObject = method.invoke(obj, new Object[0]);
                if (clazz.isInstance(keyObject)) {
                    K key = clazz.cast(keyObject);
                    uidList.add(key);
                }
            }

            return uidList;
        } catch (NoSuchMethodException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }

    public static <K, T> Set<K> getIDSetByKey(List<T> objList, Class<K> clazz, String getKeyMathName) {
        try {
            Set<K> uidList = new HashSet<K>();
            for (T obj : objList) {
                // 先获取相应的method对象,getMethod第一个参数是方法名，第二个参数是该方法的参数类型，
                Method method = obj.getClass().getMethod(getKeyMathName, new Class[0]);

                // 接下来就该执行该方法了，第一个参数是具体调用该方法的对象, 第二个参数是执行该方法的具体参数
                Object keyObject = method.invoke(obj, new Object[0]);
                if (clazz.isInstance(keyObject)) {
                    K key = clazz.cast(keyObject);
                    uidList.add(key);
                }
            }

            return uidList;
        } catch (NoSuchMethodException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }

    public static <T> List<String> getIDListByStringKey(List<T> objList, String getKeyMathName) {
        return getIDListByKey(objList, String.class, getKeyMathName);
    }

    public static <T> List<Integer> getIDListByIntegerKey(List<T> objList, String getKeyMathName) {
        return getIDListByKey(objList, Integer.class, getKeyMathName);
    }

    public static <K, T> T getObjectByKey(List<T> objList, K key, String getKeyMathName) {
        try {
            for (T obj : objList) {
                // 先获取相应的method对象,getMethod第一个参数是方法名，第二个参数是该方法的参数类型，
                Method method = obj.getClass().getMethod(getKeyMathName, new Class[0]);

                // 接下来就该执行该方法了，第一个参数是具体调用该方法的对象, 第二个参数是执行该方法的具体参数
                Object keyObject = method.invoke(obj, new Object[0]);
                if (key.getClass().isInstance(keyObject)) {
                    if (keyObject.equals(key)) {
                        return obj;
                    }
                }
            }

            return null;
        } catch (NoSuchMethodException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }
}
