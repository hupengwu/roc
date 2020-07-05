
package com.huawei.roc.keyvaluemapper;

/**
 * 更新（读操作）：访问指定的元素
 * 说明：该操作涉及到多线程，所以哈希表中的value，不能带出这个接口的外部去访问，否则会出现多线程安全问题
 * 
 * @author h00442047
 * @since 2019年11月23日
 */
public abstract class Reader {
    /**
     * 获取key
     * @return 元素的key
     */
    abstract public Object getKey();

    /**
     * 找到目标对象，并直接元素的内容
     * @param value 元素
     * @return 是否修改成功
     */
    abstract public boolean readValue(Object value);
}
