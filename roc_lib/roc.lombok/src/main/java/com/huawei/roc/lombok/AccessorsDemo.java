
package com.huawei.roc.lombok;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class AccessorsDemo {
    private Long id;

    private String name;
}
