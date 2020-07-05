
package com.huawei.roc.value.multi;

import java.util.ArrayList;
import java.util.List;

/**
 * 多重整数：内部的Integer元素是有序的
 * @author h00163887
 * 
 */
public class IntegersValue implements Cloneable {
    private List<Integer> values = new ArrayList<Integer>();

    public List<Integer> getValues() {
        return this.values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "ValuesInteger [values=" + values + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((values == null) ? 0 : values.hashCode());
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
        IntegersValue other = (IntegersValue) obj;
        if (values == null) {
            if (other.values != null) {
                return false;
            }
        } else if (!values.equals(other.values)) {
            return false;
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        IntegersValue other = (IntegersValue) super.clone();

        other.values = new ArrayList<Integer>();
        for (Integer value : this.values) {
            other.values.add(Integer.valueOf(value));
        }

        return other;
    }
}
