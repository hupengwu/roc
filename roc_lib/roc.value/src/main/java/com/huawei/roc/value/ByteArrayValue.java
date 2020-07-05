/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.value;

import java.util.Arrays;

public class ByteArrayValue implements Cloneable {
    private byte[] value = new byte[] {};

    public byte[] getValue() {
        return this.value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ByteArrayValue [value=" + Arrays.toString(value) + "]";
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ByteArrayValue other = (ByteArrayValue) obj;
        if (!Arrays.equals(value, other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ByteArrayValue other = (ByteArrayValue) super.clone();
        other.value = this.value.clone();

        return other;
    }

}