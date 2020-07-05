
package com.huawei.roc.lombok;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * https://www.cnblogs.com/heyonggang/p/8638374.html
 * @author h00442047
 * @since 2020年2月5日
 */
public class GetterSetterDemo {

    @Getter
    @Setter
    private int age = 10;

    @Setter(AccessLevel.PROTECTED)
    private String name;

    @Override
    public String toString() {
        return String.format("%s (age: %d)", name, age);
    }
}