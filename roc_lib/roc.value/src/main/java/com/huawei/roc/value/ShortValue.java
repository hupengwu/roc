
package com.huawei.roc.value;

public class ShortValue implements Cloneable {
    private short value = 0;

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
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
        ShortValue other = (ShortValue) obj;
        if (value != other.value)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ShortValue [value=" + value + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ShortValue other = (ShortValue) super.clone();
        other.value = this.value;

        return other;
    }

}
