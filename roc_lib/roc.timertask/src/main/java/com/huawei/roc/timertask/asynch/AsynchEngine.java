
package com.huawei.roc.timertask.asynch;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.huawei.roc.timertask.engine.TimerTaskActiver;
import com.huawei.roc.timertask.engine.TimerTaskEngine;

/**
 * 异步引擎：每一个Activer都有一个Engine启动(即一个线程)，使各Activer构成一个独立的任务
 * @author h00163887
 * 
 */
public class AsynchEngine {
    // private static final Logger logger = OssLogFactory.getLogger(AsynchEngine.class);

    private Map<String, Map<String, TimerTaskEngine>> class2name2activer =
        new HashMap<String, Map<String, TimerTaskEngine>>();

    /**
     * 根据类型查找Activer：默认class为名称
     * @param clazz
     * @return
     */
    public <T> T getActiverByClass(Class<T> clazz) {
        return getActiverByName(clazz, clazz.toString());
    }

    public <T> T getActiverByName(Class<T> clazz, String name) {
        Map<String, TimerTaskEngine> name2activer = class2name2activer.get(clazz.toString());
        if (name2activer == null) {
            return null;
        }

        TimerTaskEngine engine = name2activer.get(name);
        if (engine == null) {
            return null;
        }

        TimerTaskActiver activer = engine.getActiver();
        if (activer == null) {
            return null;
        }

        if (clazz.isInstance(activer)) {
            return clazz.cast(activer);
        }

        return null;
    }

    public boolean registActiver(TimerTaskActiver activer) {
        // 检查类型是否已经存在
        Map<String, TimerTaskEngine> name2activer = class2name2activer.get(activer.getClass().toString());
        if (name2activer != null) {
            return false;
        }

        // 注册类型
        name2activer = new HashMap<String, TimerTaskEngine>();
        class2name2activer.put(activer.getClass().toString(), name2activer);

        // 启动引擎:立即执行，每1秒钟处理一次请求
        TimerTaskEngine engine = new TimerTaskEngine();
        name2activer.put(activer.getClass().toString(), engine);

        engine.init(activer, activer.getDelay(), activer.getPeriod());

        return true;

    }

    public boolean registActiver(TimerTaskActiver activer, String name) {
        // 检查类型是否已经存在
        Map<String, TimerTaskEngine> name2activer = class2name2activer.get(activer.getClass().toString());
        if (name2activer == null) {
            // 注册类型
            name2activer = new HashMap<String, TimerTaskEngine>();
            class2name2activer.put(activer.getClass().toString(), name2activer);
        }

        // 检查：名称是否已经存在
        TimerTaskEngine engine = name2activer.get(name);
        if (engine != null) {
            return false;
        }

        // 启动引擎:立即执行，每1秒钟处理一次请求
        engine = new TimerTaskEngine();
        name2activer.put(name, engine);

        engine.init(activer, activer.getDelay(), activer.getPeriod());

        return true;

    }

    public boolean destory() {
        for (Entry<String, Map<String, TimerTaskEngine>> entry4class : class2name2activer.entrySet()) {
            for (Entry<String, TimerTaskEngine> entry : entry4class.getValue().entrySet()) {
                // logger.info("destory engine:" + entry.getValue());

                TimerTaskEngine engine = entry.getValue();
                engine.destroy();
            }
        }

        class2name2activer.clear();

        return true;
    }
}
