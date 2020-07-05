
package com.huawei.roc.value;

/**
 * 无符号short：使用范围更大的int保存，避免有符号的负数产生
 * 
 * @author h00442047
 * @since 2020年1月16日
 */
public class UShortValue implements Cloneable {
    private int value = 0;

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value & 0xffff;
    }

    @Override
    public String toString() {
        return "UShortValue [value=" + value + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + value;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UShortValue other = (UShortValue) obj;
        if (value != other.value) {
            return false;
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        UShortValue other = (UShortValue) super.clone();
        other.value = this.value;

        return other;
    }

}
