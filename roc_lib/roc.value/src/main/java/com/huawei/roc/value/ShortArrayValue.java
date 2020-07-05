
package com.huawei.roc.value;

import java.util.Arrays;

public class ShortArrayValue implements Cloneable {
    private short[] value = new short[] {};

    public short[] getValue() {
        return value;
    }

    public void setValue(short[] value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(value);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ShortArrayValue other = (ShortArrayValue) obj;
        if (!Arrays.equals(value, other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ShortArrayValue [value=" + Arrays.toString(value) + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ShortArrayValue other = (ShortArrayValue) super.clone();
        other.value = this.value.clone();

        return other;
    }

}