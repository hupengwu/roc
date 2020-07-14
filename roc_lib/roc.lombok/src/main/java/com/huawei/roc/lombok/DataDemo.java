
package com.huawei.roc.lombok;

import lombok.Data;
import lombok.Getter;

/**
 * https://www.cnblogs.com/heyonggang/p/8638374.html
 * @author h00442047
 * @since 2020年2月5日
 */
@Data
public class DataDemo {
   // @Getter
    private String name = "a";

  //  @Getter
    private String[] tags;

    public static void main(String[] args) throws Exception {

        DataDemo demo = new DataDemo();
        
        // 没有写get/set方法，lombok在javac编译阶段根据注解通知javac在DataDemo.class自动增加get/set方法
        String name = demo.getName();
        demo.getTags();
    }
}
