
package com.huawei.roc.restlike;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import com.huawei.roc.restlike.annotation.LinkerParam;
import com.huawei.roc.restlike.annotation.Param;
import com.huawei.roc.restlike.annotation.RequestBody;

/**
 * RESTAPI的响应者：对RESTAPI请求进行JAVA函数的响应
 * 
 * @author h00442047
 * @since 2020年1月19日
 */
public class RESTFulResponder {
    /**
     * 对RESTful请求进行响应的JAVA对象实例
     */
    private Object object;

    /**
     * 对RESTful请求进行响应的JAVA对象实例中的函数
     */
    private Method method;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object invokeMethod(Object bodyParam,
        Map<String, Object> mapParam) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return invokeMethod(null, bodyParam, mapParam);
    }

    public Object invokeMethod(RESTfulContextVO context, Object bodyParam,
        Map<String, Object> mapParam) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return invokeMethod(null, context, bodyParam, mapParam);
    }

    /**
     * 调用方法
     * 
     * @param bodyParam 给方法传递的原生body参数，给需要@RequestBody的注解处理
     * @param mapParam 从body参数解析出来的Map化参数，给需要@Param和@QueryParam的注解处理
     * @return 执行结果
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public Object invokeMethod(Object linker, RESTfulContextVO context, Object bodyParam,
        Map<String, Object> mapParam) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // Object keyObject = method.invoke(instance, new Object[0]);
        List<Object> paramList = new ArrayList<Object>();
        for (Parameter parameter : this.method.getParameters()) {
            Object arg = null;
            // @QueryParam：从mapParam中根据key取参数
            if (parameter.isAnnotationPresent(QueryParam.class)) {
                QueryParam paramAnno = parameter.getAnnotation(QueryParam.class);
                arg = mapParam.get(paramAnno.value());
                if (!arg.getClass().equals(parameter.getParameterizedType())) {
                    arg = null;
                }
                paramList.add(arg);
                continue;
            }
            // @PathParam：暂不支持
            if (parameter.isAnnotationPresent(PathParam.class)) {
                paramList.add(arg);
                continue;
            }
            // @RequestBody：取Body原文
            if (parameter.isAnnotationPresent(RequestBody.class)) {
                arg = bodyParam.toString();

                paramList.add(arg);
                continue;
            }
            // @Param：取全部参数
            if (parameter.isAnnotationPresent(Param.class)) {
                arg = mapParam;

                paramList.add(arg);
                continue;
            }
            // @Context:取上下文
            if (parameter.isAnnotationPresent(Context.class)) {
                arg = context;

                paramList.add(arg);
                continue;
            }
            // @LinkerParam：取socket等连接对象
            if (parameter.isAnnotationPresent(LinkerParam.class)) {
                arg = linker;

                paramList.add(arg);
                continue;
            }

            paramList.add(arg);
            continue;
        }

        return this.method.invoke(this.object, paramList.toArray());
    }

    public Object checkMethodAnno(Class<? extends Annotation> annoClass) {
        for (Parameter parameter : this.method.getParameters()) {
            // 检查：参数上是否有该注解
            if (!parameter.isAnnotationPresent(annoClass)) {
                continue;
            }

            // 检查：是否为支持的注解

            // @QueryParam：从mapParam中根据key取参数
            if (QueryParam.class.equals(annoClass)) {
                return true;
            }
            // @PathParam：暂不支持
            if (PathParam.class.equals(annoClass)) {
                return true;
            }
            // @RequestBody：取Body原文
            if (RequestBody.class.equals(annoClass)) {
                return true;
            }
            // @Param：取全部参数
            if (Param.class.equals(annoClass)) {
                return true;
            }
            // @Context:取上下文
            if (Context.class.equals(annoClass)) {
                return true;
            }
            // @LinkerParam：取socket等连接对象
            if (LinkerParam.class.equals(annoClass)) {
                return true;
            }

            continue;
        }

        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((method == null) ? 0 : method.hashCode());
        result = prime * result + ((object == null) ? 0 : object.hashCode());
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
        RESTFulResponder other = (RESTFulResponder) obj;
        if (method == null) {
            if (other.method != null)
                return false;
        } else if (!method.equals(other.method))
            return false;
        if (object == null) {
            if (other.object != null)
                return false;
        } else if (!object.equals(other.object))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "RESTFulResponder [object=" + object + ", method=" + method + "]";
    }

}
