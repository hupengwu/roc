
package com.huawei.roc.value.multi;

import java.util.ArrayList;
import java.util.List;

/**
 * 多重字符串：内部的String元素是有序的
 * @author h00163887
 * 
 */
public class StringsValue implements Cloneable {
    private List<String> values = new ArrayList<String>();

    public List<String> getValues() {
        return this.values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "ValuesString [values=" + values + "]";
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
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StringsValue other = (StringsValue) obj;
        if (values == null) {
            if (other.values != null)
                return false;
        } else if (!values.equals(other.values))
            return false;
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        StringsValue other = (StringsValue) super.clone();

        other.values = new ArrayList<String>();
        for (String value : this.values) {
            other.values.add(String.valueOf(value));
        }

        return other;
    }
}