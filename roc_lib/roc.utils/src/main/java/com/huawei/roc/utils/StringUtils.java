/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 * @author h00163887
 * @since 2019/11/16
 */
public class StringUtils {
    private static StringUtils instance = new StringUtils();

    public static StringUtils getInstance() {
        return instance;
    }

    /**
     * 字符串格式：1,2,3,4,5,6,7或[1,2,3,4,5,6,7]格式
     * @param intListString 1,2,3,4,5,6,7或[1,2,3,4,5,6,7]格式的字符串
     * @return 1,2,3,4,5,6,7的Integer数组列表
     */
    public List<Integer> string2IntList(String intListString) {
        if (null == intListString || intListString.isEmpty()) {
            return new ArrayList<Integer>();
        }

        intListString = intListString.replace("[", "");
        intListString = intListString.replace("]", "");
        intListString = intListString.replace(" ", "");

        if (intListString.isEmpty()) {
            return new ArrayList<Integer>();
        }

        List<Integer> intList = new ArrayList<Integer>();

        String[] intStringArray = intListString.split(",");
        if (intStringArray.length < 1) {
            return intList;
        }

        for (int i = 0; i < intStringArray.length; i++) {
            if (intStringArray[i].isEmpty()) {
                continue;
            }

            Integer hopID = Integer.valueOf(intStringArray[i]);
            intList.add(hopID);
        }

        return intList;
    }

    /**
     * 字符串格式：1,2,3,4,5,6,7或[1,2,3,4,5,6,7]格式
     * @param intList Integer数组列表
     * @return 1,2,3,4,5,6,7或[1,2,3,4,5,6,7]格式的字符串
     */
    public String intList2String(List<Integer> intList) {
        String intListString = intList.toString();
        intListString = intListString.replace("[", "");
        intListString = intListString.replace("]", "");
        return intListString;
    }

    public String intList2String(List<Integer> intList, String regex) {
        String intListString = intList2String(intList);
        intListString = intListString.replace(",", regex);
        return intListString;
    }

    /**
     * 字符串格式："a,b,c,d,e,f,g"格式的字符串
     * @param stringListString "a,b,c,d,e,f,g"格式的字符串
     * @return "a","b","c","d","e","f","g"的String数组列表
     */
    public List<String> string2StringList(String stringListString) {
        return string2StringList(stringListString, ",");
    }

    public List<String> string2StringList(String stringListString, String regex) {
        // 空字符串则直接返回
        if (null == stringListString || stringListString.isEmpty()) {
            return new ArrayList<String>();
        }

        String[] stringArray = stringListString.split(regex);

        List<String> stringList = new ArrayList<String>();
        for (int i = 0; i < stringArray.length; i++) {
            stringList.add(stringArray[i]); // NOPMD
        }

        return stringList;
    }

    /**
     * 字符串格式：1,2,3,4,5,6,7或[1,2,3,4,5,6,7]格式
     * @param intList "a","b","c","d","e","f","g"的String数组列表
     * @return 1,2,3,4,5,6,7或[1,2,3,4,5,6,7]格式的字符串
     */
    public String stringList2String(List<String> intList) {
        String valueList = "";
        StringBuffer sb = new StringBuffer();
        for (String value : intList) {
            sb.append(value);
            sb.append(",");
        }
        valueList = sb.toString();
        if (valueList.isEmpty()) {
            return valueList;
        }
        valueList = valueList.substring(0, valueList.length() - 1);
        return valueList;
    }

    public String stringList2String(Set<String> intList) {
        String valueList = "";
        StringBuffer sb = new StringBuffer();
        for (String value : intList) {
            sb.append(value);
            sb.append(",");
        }
        valueList = sb.toString();
        if (valueList.isEmpty()) {
            return valueList;
        }
        valueList = valueList.substring(0, valueList.length() - 1);
        return valueList;
    }

    public List<Double> string2DoubleList(String doubleListString) {
        if (null == doubleListString || doubleListString.isEmpty()) {
            return new ArrayList<Double>();
        }

        doubleListString = doubleListString.replace("[", "");
        doubleListString = doubleListString.replace("]", "");
        doubleListString = doubleListString.replace(" ", "");

        if (doubleListString.isEmpty()) {
            return new ArrayList<Double>();
        }

        List<Double> doubleList = new ArrayList<Double>();

        String[] doubleStringArray = doubleListString.split(",");
        if (doubleStringArray.length < 1) {
            return doubleList;
        }

        for (int i = 0; i < doubleStringArray.length; i++) {
            if (doubleStringArray[i].isEmpty()) {
                continue;
            }

            Double hopID = Double.valueOf(doubleStringArray[i]);
            doubleList.add(hopID);
        }

        return doubleList;
    }

    /**
     * 字符串格式：1,2,3,4,5,6,7或[1,2,3,4,5,6,7]格式
     * @param intList Integer数组列表
     * @return 1,2,3,4,5,6,7或[1,2,3,4,5,6,7]格式的字符串
     */
    public String doubleList2String(List<Double> intList) {
        String intListString = intList.toString();
        intListString = intListString.replace("[", "");
        intListString = intListString.replace("]", "");
        return intListString;
    }

    public String doubleList2String(List<Double> intList, String regex) {
        String intListString = doubleList2String(intList);
        intListString = intListString.replace(",", regex);
        return intListString;
    }

    public String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            if (str.equalsIgnoreCase("/")) {
                return dest;
            }
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
