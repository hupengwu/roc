
package com.huawei.roc.channel.master.boot;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * @author Kent Lee
 * @description 解析测试环境的配置文件，由于项目会打包成jar所以需要注入ResourceLoader
 * @create 2018-08-03 17:16
 * @contact kent1411390610@gmail.com
 **/
public class EnvResolver {
    private ResourceLoader resourceLoader;

    public EnvResolver() {
        resourceLoader = new DefaultResourceLoader();
    }

    public List GetEnvConfig() throws IOException {

        Resource resource = resourceLoader.getResource("lib:roc.channel.master.impl-1.0.0-SNAPSHOT.jar:config/roc_channel_mater.core.impl.services.xml");
        System.out.println("文件名称: " + resource.getFilename());
        InputStream inputStream = resource.getInputStream();
    
        inputStream.close();
        return null;
    }
}
