
package com.huawei.roc.value;

/**
 * 无符号BYTE：使用范围更大的short保存，避免有符号的负数产生
 * 
 * @author h00442047
 * @since 2020年1月16日
 */
public class UByteValue implements Cloneable {
    private short value = 0;

    public short getValue() {
        return this.value;
    }

    public void setValue(short value) {
        this.value = (short) (value & 0xff);
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
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UByteValue other = (UByteValue) obj;
        if (value != other.value)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UByteValue [value=" + value + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        UByteValue other = (UByteValue) super.clone();
        other.value = this.value;

        return other;
    }

}
